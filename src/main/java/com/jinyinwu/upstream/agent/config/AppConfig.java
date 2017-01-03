package com.jinyinwu.upstream.agent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class AppConfig implements Serializable {

    @Value("${zookeeper.quorum}")
    private String zookeeperQuorum = "";

    @Value("${zookeeper.namespace}")
    private String zookeeperNamespace = "";

    @Value("${zookeeper.discoveryPath}")
    private String zookeeperDiscoveryPath = "";


    @Value("${nginx.template.file}")
    private String nginxTemplateFile = "";

    @Value("${nginx.pid.file}")
    private String nginxPidFile = "";

    @Value("${clusters}")
    private String clusters = "";

    public String getZookeeperQuorum() {
        return zookeeperQuorum;
    }

    public void setZookeeperQuorum(String zookeeperQuorum) {
        this.zookeeperQuorum = zookeeperQuorum;
    }

    public String getZookeeperNamespace() {
        return zookeeperNamespace;
    }

    public void setZookeeperNamespace(String zookeeperNamespace) {
        this.zookeeperNamespace = zookeeperNamespace;
    }

    public String getZookeeperDiscoveryPath() {
        return zookeeperDiscoveryPath;
    }

    public void setZookeeperDiscoveryPath(String zookeeperDiscoveryPath) {
        this.zookeeperDiscoveryPath = zookeeperDiscoveryPath;
    }

    public String getClusters() {
        return clusters;
    }

    public void setClusters(String clusters) {
        this.clusters = clusters;
    }

    public String getNginxTemplateFile() {
        return nginxTemplateFile;
    }

    public void setNginxTemplateFile(String nginxTemplateFile) {
        this.nginxTemplateFile = nginxTemplateFile;
    }

    public String getNginxPidFile() {
        return nginxPidFile;
    }

    public void setNginxPidFile(String nginxPidFile) {
        this.nginxPidFile = nginxPidFile;
    }
}