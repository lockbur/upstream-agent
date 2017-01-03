package com.jinyinwu.upstream.agent.handler;


import com.jinyinwu.upstream.agent.data.Cluster;
import com.jinyinwu.upstream.agent.data.RoutingConfiguration;

import java.io.IOException;
import java.util.List;

public interface NginxConfigWriter {
    void writeConfig(List<Cluster> clusters);
//    void writeConfig(RoutingConfiguration routingConfiguration) throws IOException;
}
