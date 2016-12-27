package com.jinyinwu.upstream.agent.data;

import com.google.common.collect.Maps;
import java.io.Serializable;
import java.util.Map;

public class Location implements Serializable {
    private String context;
    private Cluster upstream;
    Map<String, String> attributes = Maps.newHashMap();

    public Location(String context, Cluster upstream) {
        this.context = context;
        this.upstream = upstream;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Cluster getUpstream() {
        return upstream;
    }

    public void setUpstream(Cluster upstream) {
        this.upstream = upstream;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
