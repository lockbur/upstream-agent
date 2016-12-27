package com.jinyinwu.upstream.agent.nginx;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.jinyinwu.upstream.agent.TestBase;
import com.jinyinwu.upstream.agent.config.AppConfig;
import com.jinyinwu.upstream.agent.data.Cluster;
import com.jinyinwu.upstream.agent.data.Location;
import com.jinyinwu.upstream.agent.data.RoutingConfiguration;
import com.jinyinwu.upstream.agent.data.Server;
import com.jinyinwu.upstream.agent.handler.ConfigWriter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Created by wangkun23 on 2016/12/15.
 */
public class NginxConfigTest extends TestBase {
    private static final Logger log = LoggerFactory.getLogger(NginxConfigTest.class);

    @Resource
    private ConfigWriter configWriter;

    //@Test
    public void test() throws IOException {
        ImmutableList.Builder<Server> servers = ImmutableList.builder();

        Server server1 =   new Server("127.0.0.1", 8080);
        Server server2 =   new Server("127.0.0.1", 8081);
        servers.add(server1);
        servers.add(server2);


        List<Cluster> clusters = Lists.newArrayListWithExpectedSize(1);
        Cluster cluster1= Cluster.builder()
                .clusterName("tomcat001")
                .protocol("http")
                .port(8080)
                .servers(servers.build())
                .build();

        Cluster cluster2= Cluster.builder()
                .clusterName("tomcat002")
                .protocol("http")
                .port(8081)
                .servers(servers.build())
                .build();

        clusters.add(cluster1);
        clusters.add(cluster2);


        configWriter.writeConfig(buildConfigContext(clusters));
    }

    private RoutingConfiguration buildConfigContext(List<Cluster> clusters) {
        ImmutableList.Builder<Location> locations = ImmutableList.builder();
        for (Cluster cluster : clusters) {
            log.info("({}) Locating contexts for {}://{}:{}, {} servers in group",
                    cluster.getClusterName(), cluster.getProtocol(), cluster.getClusterName(), cluster.getPort(),
                    cluster.getServers().size());

           // for (String context : AppConfig.getStringList("cluster." + cluster.getClusterName() + ".context")) {
                //log.info("({}) Adding context=[{}]", cluster.getClusterName(), context);
                locations.add(new Location("/center", cluster));
                locations.add(new Location("/dingtalk", cluster));
           // }
        }

        return new RoutingConfiguration(clusters, locations.build());
    }


}
