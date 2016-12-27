package com.jinyinwu.upstream.agent.nginx;

import com.google.common.eventbus.Subscribe;
import com.jinyinwu.upstream.agent.config.AppConfig;
import com.jinyinwu.upstream.agent.events.ConfigWrittenEvent;
import com.jinyinwu.upstream.agent.handler.PostConfigCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;


public class NginxReloaderCallback implements PostConfigCallback {
    private static final Logger log = LoggerFactory.getLogger(NginxReloaderCallback.class);

    private final NginxManager nginxManager;

    public NginxReloaderCallback() throws FileNotFoundException {
        this.nginxManager = new NginxManager(AppConfig.getString("nginx.pid-file"));
    }

    @Subscribe
    @Override
    public void onConfigFinished(ConfigWrittenEvent event) {
        try {
            nginxManager.reloadNginx();
        } catch (InvalidPidException | FileNotFoundException e) {
            log.error("ERROR: Failed to reload NGINX", e);
        }
    }
}
