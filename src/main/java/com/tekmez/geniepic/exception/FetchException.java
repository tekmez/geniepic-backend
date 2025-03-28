package com.tekmez.geniepic.exception;

/**
 * Exception thrown when AI service result fetching fails
 */
public class FetchException extends AIProcessingException {
    private static final String ERROR_CODE = "FETCH_ERROR";

    public FetchException(String serviceName, String requestId, Throwable cause) {
        super(serviceName + " result fetch failed for request ID: " + requestId, ERROR_CODE, cause);
    }
} 