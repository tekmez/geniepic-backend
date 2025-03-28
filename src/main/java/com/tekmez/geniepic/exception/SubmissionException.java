package com.tekmez.geniepic.exception;

/**
 * Exception thrown when AI service request submission fails
 */
public class SubmissionException extends AIProcessingException {
    private static final String ERROR_CODE = "SUBMISSION_ERROR";

    public SubmissionException(String serviceName, Throwable cause) {
        super(serviceName + " request submission failed", ERROR_CODE, cause);
    }

} 