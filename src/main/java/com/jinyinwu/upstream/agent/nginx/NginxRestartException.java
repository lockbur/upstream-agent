package com.jinyinwu.upstream.agent.nginx;


public class NginxRestartException extends RuntimeException {
    public NginxRestartException(String message) {
        super(message);
    }

    public NginxRestartException(String message, Throwable cause) {
        super(message, cause);
    }

    public NginxRestartException(Throwable cause) {
        super(cause);
    }
}
