package com.jinyinwu.upstream.agent.nginx;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.common.eventbus.Subscribe;
import com.jinyinwu.upstream.agent.config.AppConfig;
import com.jinyinwu.upstream.agent.data.RoutingConfiguration;
import com.jinyinwu.upstream.agent.handler.ConfigWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.Date;

@Service
public class NginxConfigWriter implements ConfigWriter {
    private static final Logger log = LoggerFactory.getLogger(NginxConfigWriter.class);

    @Resource
    private MustacheFactory mustacheFactory;
    private int timesWritten;
    private Date lastWritten;

    @Override
    public void writeConfig(RoutingConfiguration routingConfiguration) throws IOException {

        Reader reader;
        try {
            reader = new FileReader(new File(AppConfig.getString("nginx.template-file")));
        } catch (FileNotFoundException e) {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("nginx.conf.mustache");
            if (is == null) {
                log.info("NGINX config null");
                throw e;
            }
            reader = new InputStreamReader(is);
        }
        Mustache mustache = mustacheFactory.compile(reader, "nginx_conf");

        String filename = AppConfig.getString("nginx.config-file");

        log.info("Flushing NGINX config to {}", filename);

        mustache.execute(new FileWriter("/var/nginx.conf"), routingConfiguration).flush();

        timesWritten++;
        lastWritten = new Date();
    }

    public int getTimesWritten() {
        return timesWritten;
    }

    public Date getLastWritten() {
        return lastWritten;
    }
}
