package com.jinyinwu.upstream.agent.handler;


import com.jinyinwu.upstream.agent.events.ConfigWrittenEvent;

public interface PostConfigCallback {
    void onConfigFinished(ConfigWrittenEvent event);
}
