package com.jinyinwu.upstream.agent.nginx;

import com.jinyinwu.upstream.agent.config.AppConfig;
import com.jinyinwu.upstream.agent.events.ConfigWrittenEvent;
import com.jinyinwu.upstream.agent.handler.PostConfigCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

//@Component
public class NginxReloaderCallback implements PostConfigCallback {

    final Logger log = LoggerFactory.getLogger(NginxReloaderCallback.class);


    @Autowired
    private NginxManager nginxManager;

    @Override
    public void onConfigFinished(ConfigWrittenEvent event) {
        try {
            nginxManager.reloadNginx();
        } catch (InvalidPidException | FileNotFoundException e) {
            log.error("ERROR: Failed to reload NGINX", e);
        }
    }
}
