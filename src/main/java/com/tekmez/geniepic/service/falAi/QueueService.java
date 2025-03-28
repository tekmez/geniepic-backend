package com.tekmez.geniepic.service.falAi;

import com.tekmez.geniepic.exception.AIProcessingException;
import com.tekmez.geniepic.interfaces.IAIService;
import com.tekmez.geniepic.model.dto.ImageReqDto;
import com.tekmez.geniepic.model.dto.ImageResponseDto;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

@Log4j2
public class QueueService<T extends IAIService> {
    private final T aiService;

    public QueueService(T aiService) {
        this.aiService = aiService;
    }

    private static final String COMPLETED_STATUS = "COMPLETED";
    private static final long POLLING_INTERVAL_SECONDS = 1;



    public ImageResponseDto processRequest(ImageReqDto payload) {
        try {
            log.info("Processing new request");
            String requestId = aiService.submitRequest(payload);
            log.info("Request submitted with ID: {}", requestId);
            
            return waitForCompletion(requestId);
        } catch (AIProcessingException ex) {
            log.error("AI processing error: {}", ex.getMessage(), ex);
            throw ex; // Rethrow custom exceptions as-is
        } catch (Exception e) {
            log.error("Unhandled error processing request: {}", e.getMessage(), e);
            throw new AIProcessingException("Error processing AI request: " + e.getMessage(), "PROCESSING_ERROR", e);
        }
    }
    
    private ImageResponseDto waitForCompletion(String requestId) {
        try {
            String status = aiService.checkStatus(requestId);
            log.debug("Initial status for request {}: {}", requestId, status);
            
            // Wait until we get COMPLETED status
            while (!COMPLETED_STATUS.equals(status)) {
                TimeUnit.SECONDS.sleep(POLLING_INTERVAL_SECONDS);
                status = aiService.checkStatus(requestId);
                log.debug("Status for request {}: {}", requestId, status);
            }
            
            log.info("Request {} completed successfully", requestId);
            return aiService.fetchResult(requestId);
        } catch (InterruptedException e) {
            log.error("Request processing interrupted: {}", e.getMessage());
            Thread.currentThread().interrupt(); // Good practice for handling interrupts
            throw new AIProcessingException("Processing interrupted", "INTERRUPTED", e);
        }
    }
}
