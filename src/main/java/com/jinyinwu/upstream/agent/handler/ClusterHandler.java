package com.jinyinwu.upstream.agent.handler;


import com.jinyinwu.upstream.agent.events.ClustersUpdatedEvent;

public interface ClusterHandler {
    void processClusters(ClustersUpdatedEvent event);
}
