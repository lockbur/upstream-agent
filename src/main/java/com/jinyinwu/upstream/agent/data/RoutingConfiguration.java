package com.jinyinwu.upstream.agent.data;

import com.google.common.collect.ImmutableList;
import java.util.List;


public class RoutingConfiguration {
    private final ImmutableList<Cluster> clusters;
    private final ImmutableList<Location> locations;

    public RoutingConfiguration(List<Cluster> clusters, List<Location> locations) {
        this.clusters = ImmutableList.copyOf(clusters);
        this.locations = ImmutableList.copyOf(locations);
    }

    public ImmutableList<Cluster> getClusters() {
        return clusters;
    }

    public ImmutableList<Location> getLocations() {
        return locations;
    }


}
