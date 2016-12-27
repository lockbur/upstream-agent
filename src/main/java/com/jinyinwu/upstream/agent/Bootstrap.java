package com.jinyinwu.upstream.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 聊天服务器端程序 启动
 * Created by wangkun23 on 2016/7/25.
 */
public class Bootstrap {

    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) throws InterruptedException {


        AbstractApplicationContext cxt = new FileSystemXmlApplicationContext("classpath:spring-config.xml");

        //重要  在关闭jvm之前 需要关闭socket.io的服务器
        logger.info("netty socket.io stopped.");
        cxt.registerShutdownHook();
    }

}
