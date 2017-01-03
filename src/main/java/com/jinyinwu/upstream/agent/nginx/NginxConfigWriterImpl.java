package com.jinyinwu.upstream.agent.nginx;

import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.common.collect.ImmutableList;
import com.jinyinwu.upstream.agent.config.AppConfig;
import com.jinyinwu.upstream.agent.data.Cluster;
import com.jinyinwu.upstream.agent.data.Location;
import com.jinyinwu.upstream.agent.data.RoutingConfiguration;
import com.jinyinwu.upstream.agent.handler.NginxConfigWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.Date;
import java.util.List;

@Service
public class NginxConfigWriterImpl implements NginxConfigWriter {
    private static final Logger log = LoggerFactory.getLogger(NginxConfigWriterImpl.class);

    @Resource
    private MustacheFactory mustacheFactory;

    @Autowired
    private AppConfig appConfig;

    private int timesWritten;
    private Date lastWritten;


    @Override
    public void writeConfig(List<Cluster> clusters){
        RoutingConfiguration routingConfiguration =  buildConfigContext(clusters);
        Reader reader;
        try {
            reader = new FileReader(new File(appConfig.getNginxTemplateFile()));
        } catch (FileNotFoundException e) {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("nginx.conf.mustache");
            if (is == null) {
                log.info("NGINX config null {}",e);
            }
            reader = new InputStreamReader(is);
        }

        Mustache mustache = mustacheFactory.compile(reader, "nginx_conf");

        String filename = appConfig.getNginxPidFile();

        log.info("Flushing NGINX config to {}", filename);

        try {
            mustache.execute(new FileWriter("/var/nginx.conf"), routingConfiguration).flush();
        } catch (IOException e) {
            log.info("NGINX config mustache {}",e);
        }
        timesWritten++;
        lastWritten = new Date();
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

    public int getTimesWritten() {
        return timesWritten;
    }

    public Date getLastWritten() {
        return lastWritten;
    }
}
