package com.jinyinwu.upstream.agent.data;

import java.io.Serializable;

public class Server implements Serializable {
    String host;
    Integer port;

    public Server(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
