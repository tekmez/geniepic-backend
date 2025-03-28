package com.tekmez.geniepic.exception;

import lombok.Getter;

/**
 * Base exception class for AI processing errors
 */
@Getter
public class AIProcessingException extends RuntimeException {
    private final String errorCode;

    public AIProcessingException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
} 