package com.jinyinwu.upstream.agent.data;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


public class Cluster implements Serializable {
    private final String protocol;
    private final String clusterName;
    private final int port;
    private final List<Server> servers;

    public Cluster(Builder builder) {
        checkArgument(!Strings.isNullOrEmpty(builder.protocol.trim()), "protocol must be set");
        checkArgument(!Strings.isNullOrEmpty(builder.clusterName.trim()), "clusterName must be set");
        checkNotNull(builder.servers, "servers must be set");

        this.protocol = builder.protocol;
        this.clusterName = builder.clusterName;
        this.port = builder.port;
        this.servers = new ArrayList<>(builder.servers);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String protocol = "http";
        private String clusterName;
        private int port = 80;
        private Collection<Server> servers;

        private Builder() {}

        public Builder protocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder clusterName(String clusterName) {
            this.clusterName = clusterName;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder servers(Collection<Server> servers) {
            this.servers = servers;
            return this;
        }

        public Cluster build() {
            return new Cluster(this);
        }
    }

    public String getProtocol() {
        return protocol;
    }

    public String getClusterName() {
        return clusterName;
    }

    public int getPort() {
        return port;
    }

    public List<Server> getServers() {
        return servers;
    }
}
