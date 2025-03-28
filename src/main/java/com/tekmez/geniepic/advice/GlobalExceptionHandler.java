package com.tekmez.geniepic.advice;

import com.tekmez.geniepic.exception.AIProcessingException;
import com.tekmez.geniepic.exception.FetchException;
import com.tekmez.geniepic.exception.StatusCheckException;
import com.tekmez.geniepic.exception.SubmissionException;
import com.tekmez.geniepic.model.dto.ErrorResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global exception handler for centralized error management
 */
@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    /**
     * Get request path from web request
     */
    private String getRequestPath(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }

    /**
     * Creates error response entity with given parameters
     */
    private ResponseEntity<ErrorResponseDto> createErrorResponse(
            String message, String errorCode, HttpStatus status, WebRequest request) {
        String path = getRequestPath(request);
        ErrorResponseDto errorResponse = new ErrorResponseDto(message, errorCode, path);
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(AIProcessingException.class)
    public ResponseEntity<ErrorResponseDto> handleAIProcessingException(
            AIProcessingException ex, 
            WebRequest request) {
        
        log.error("AI Processing error: {}", ex.getMessage(), ex);
        
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        
        // Custom status codes based on exception type
        if (ex instanceof SubmissionException) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
        } else if (ex instanceof FetchException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (ex instanceof StatusCheckException) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
        }
        
        return createErrorResponse(ex.getMessage(), ex.getErrorCode(), status, request);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            WebRequest request) {
        
        log.warn("Validation error: {}", ex.getMessage());
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        String errorMessage = errors.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));
        
        return createErrorResponse(errorMessage, "VALIDATION_ERROR", HttpStatus.BAD_REQUEST, request);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(
            Exception ex,
            WebRequest request) {
        
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return createErrorResponse(
                "An unexpected error occurred: " + ex.getMessage(),
                "INTERNAL_SERVER_ERROR",
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }
} 