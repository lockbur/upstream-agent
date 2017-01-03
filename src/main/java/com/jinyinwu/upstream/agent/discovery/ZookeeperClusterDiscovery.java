package com.jinyinwu.upstream.agent.discovery;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.jinyinwu.upstream.agent.data.Cluster;
import com.jinyinwu.upstream.agent.data.Server;
import com.jinyinwu.upstream.agent.data.ServiceMetadata;
import com.jinyinwu.upstream.agent.handler.NginxConfigWriter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.BoundedExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceCache;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.ServiceCacheListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
@Service
public class ZookeeperClusterDiscovery extends AbstractClusterDiscovery {


    private List<ServiceCache<?>> serviceCacheList = Lists.newArrayList();

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    private ServiceDiscovery<ServiceMetadata> curatorServiceDiscovery;

    @Resource
    private NginxConfigWriter nginxConfigWriter;

    @Override
    @PostConstruct
    public void startUp() throws Exception {
        initializeServiceCaches();
        updateInstances(false);
    }

    @Override
    public void shutDown() throws Exception {
        log.info("Shutting down...");
    }

    private void initializeServiceCaches() throws Exception {
        // Close any caches (if exists)
        for (ServiceCache<?> cache : serviceCacheList) {
            cache.close();
        }

        serviceCacheList = Lists.newArrayList();

        Collection<String> serviceNames = curatorServiceDiscovery.queryForNames();

        for (final String serviceName : serviceNames) {
            log.info("serviceName {}", serviceName);

           final ServiceCache<?> cache = curatorServiceDiscovery.serviceCacheBuilder().name(serviceName).build();

            cache.addListener(new ServiceCacheListener() {
                public void cacheChanged() {
                    log.info("Service {} modified, rewriting config", serviceName);
                    updateInstances(false);
                }
                public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {

                }
            }, executorService);

            cache.start();
            serviceCacheList.add(cache);
        }
    }

    @Override
    public List<Cluster> getClusters() {
        List<Cluster> clusterList = Lists.newArrayListWithExpectedSize(serviceCacheList.size());
        for (ServiceCache<?> cache : serviceCacheList) {
            if (!cache.getInstances().isEmpty()) {
                Cluster cluster = clusterFromInstances(cache.getInstances());

                for (ServiceInstance<?> instance : cache.getInstances()) {
                    log.info("###$$$%% cache instance {}", instance.getAddress());
                    cluster.getServers().add(convertInstance(instance));
                }

                log.info("Discovery: cluster=[{}] has {} instances, {}...", cluster.getClusterName(), cluster.getServers().size(), Iterables.limit(cluster.getServers(), 5));

                clusterList.add(cluster);
            }
        }

        nginxConfigWriter.writeConfig(clusterList);
        return clusterList;
    }

    private <T> Cluster clusterFromInstances(List<ServiceInstance<T>> instances) {
        ImmutableList.Builder<Server> servers = ImmutableList.builder();
        for (ServiceInstance<?> instance : instances) {
            log.info("### instance {}", instance.getAddress());
            servers.add(convertInstance(instance));
        }

        ServiceInstance<?> instance = instances.get(0);

        return Cluster
                .builder()
                .clusterName(instance.getName())
                .protocol(instance.getSslPort() != null ? "https" : "http")
                .port(instance.getPort())
                .servers(servers.build())
                .build();
    }

    protected Server convertInstance(ServiceInstance<?> instance) {
        Integer port = instance.getSslPort() != null ? instance.getSslPort() : instance.getPort();
        return new Server(instance.getAddress(), port);
    }

    private class ReInitRunnable implements Runnable {
        public void run() {
            try {
                updateInstances(false);
            } catch (Exception e) {
                log.error("Error re-initializing with new configuration, {}", e.getMessage(), e);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        CuratorFramework zk = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1")
                .connectionTimeoutMs(2000)
                .retryPolicy(new BoundedExponentialBackoffRetry(100, 10000, 100))
                .namespace("discovery")
                .build();

        zk.start();

        ServiceDiscovery<Void> dsc = ServiceDiscoveryBuilder.builder(Void.class)
                .basePath("services")
                .client(zk)
                .build();

        dsc.start();

        dsc.registerService(ServiceInstance.<Void>builder().id("a")
                .name("service001")
                .address("128.0.0.1").port(6667).build());

        dsc.registerService(ServiceInstance.<Void>builder().id("b")
                .name("service002")
                .address("128.0.0.1").port(6667).build());
    }
}
