package com.jinyinwu.upstream.agent.discovery;

import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.AbstractIdleService;

import com.jinyinwu.upstream.agent.data.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Collections;
import java.util.List;


public abstract class AbstractClusterDiscovery extends AbstractIdleService {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected List<Cluster> previousClusterList = Collections.emptyList();

    public abstract void startUp() throws Exception;

    public abstract void shutDown() throws Exception;


    public void forceUpdate() {
        updateInstances(true);
    }

    protected void updateInstances(boolean force) {
        List<Cluster> clusterList = getClusters();
//        if (force || previousClusterList != clusterList) {
//            bus.post(new ClustersUpdatedEvent(clusterList));
//        }
    }

    public abstract List<Cluster> getClusters();
}
