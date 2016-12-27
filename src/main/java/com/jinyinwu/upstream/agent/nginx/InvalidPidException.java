package com.jinyinwu.upstream.agent.nginx;


public class InvalidPidException extends RuntimeException {
    public InvalidPidException(String message, Throwable cause) {
        super(message, cause);
    }
}
