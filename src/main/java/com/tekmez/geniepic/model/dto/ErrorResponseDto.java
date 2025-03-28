package com.tekmez.geniepic.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Standardized error response DTO for API responses
 */
@Getter
@Setter
public class ErrorResponseDto {
    private final String message;
    private final String errorCode;
    private final Instant timestamp;
    private final String path;

    public ErrorResponseDto(String message, String errorCode, String path) {
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = Instant.now();
        this.path = path;
    }
} 