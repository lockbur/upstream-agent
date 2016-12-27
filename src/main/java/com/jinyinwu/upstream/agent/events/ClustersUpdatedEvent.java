package com.jinyinwu.upstream.agent.events;

import com.google.common.collect.ImmutableList;
import com.jinyinwu.upstream.agent.data.Cluster;
import java.util.List;

public class ClustersUpdatedEvent {
    private ImmutableList<Cluster> clusters;

    public ClustersUpdatedEvent(List<Cluster> clusters) {
        this.clusters = ImmutableList.copyOf(clusters);
    }

    public ImmutableList<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(ImmutableList<Cluster> clusters) {
        this.clusters = clusters;
    }
}
