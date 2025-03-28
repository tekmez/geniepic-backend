package com.tekmez.geniepic.exception;

/**
 * Exception thrown when AI service status check fails
 */
public class StatusCheckException extends AIProcessingException {
    private static final String ERROR_CODE = "STATUS_CHECK_ERROR";

    public StatusCheckException(String serviceName, String requestId, Throwable cause) {
        super(serviceName + " status check failed for request ID: " + requestId, ERROR_CODE, cause);
    }
} 