package com.jinyinwu.upstream.agent;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.BoundedExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangkun23 on 2016/12/27.
 */
public class ZookeeperClientTest {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {

        CuratorFramework zk= CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1")
                .connectionTimeoutMs(2000)
                .retryPolicy(new BoundedExponentialBackoffRetry(100, 10000, 100))
                .namespace("discovery")
                .build();

        zk.start();
    }

}
