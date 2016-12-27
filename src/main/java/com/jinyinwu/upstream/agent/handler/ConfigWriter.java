package com.jinyinwu.upstream.agent.handler;


import com.jinyinwu.upstream.agent.data.RoutingConfiguration;

import java.io.IOException;

public interface ConfigWriter {
    void writeConfig(RoutingConfiguration routingConfiguration) throws IOException;
}
