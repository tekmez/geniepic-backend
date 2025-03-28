package com.tekmez.geniepic.service.falAi;

import ai.fal.client.FalClient;
import ai.fal.client.queue.QueueResultOptions;
import ai.fal.client.queue.QueueStatusOptions;
import com.google.gson.JsonObject;
import com.tekmez.geniepic.exception.FetchException;
import com.tekmez.geniepic.exception.StatusCheckException;
import com.tekmez.geniepic.model.dto.ImageResponseDto;

public abstract class BaseFalAIService {

    protected final FalClient fal = FalClient.withEnvCredentials();
    protected final String serviceName;
    protected final String baseUrl;

    protected BaseFalAIService(String serviceName, String baseUrl) {
        this.serviceName = serviceName;
        this.baseUrl = baseUrl;
    }

    protected ImageResponseDto getFalImageResponse(String requestId) {
        try {
            var result = fal.queue().result(baseUrl, QueueResultOptions.withRequestId(requestId));
            JsonObject imageObject = result.getData().get("image").getAsJsonObject();
            String imageUrl = imageObject.get("url").getAsString();
            return new ImageResponseDto(requestId, imageUrl);
        } catch (Exception e) {
            throw new FetchException(serviceName, requestId, e);
        }
    }

    public String checkStatus(String requestId) {
        try {
            var status = fal.queue().status(baseUrl, QueueStatusOptions.withRequestId(requestId));
            return status.getStatus().name();
        } catch (Exception e) {
            throw new StatusCheckException(serviceName, requestId, e);
        }
    }
} 