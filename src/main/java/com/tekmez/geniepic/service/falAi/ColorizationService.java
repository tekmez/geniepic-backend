package com.tekmez.geniepic.service.falAi;

import com.google.gson.JsonObject;
import com.tekmez.geniepic.exception.SubmissionException;
import com.tekmez.geniepic.interfaces.IColorizationService;
import com.tekmez.geniepic.model.dto.ImageReqDto;
import com.tekmez.geniepic.model.dto.ImageResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ai.fal.client.queue.*;

import java.util.Map;

@Service
@Log4j2
public class ColorizationService extends BaseFalAIService implements IColorizationService {

    protected ColorizationService() {
        super("Colorization", "fal-ai/ddcolor");
    }

    @Override
    public String submitRequest(ImageReqDto payload) throws SubmissionException {
        try {
            var input = Map.of(
                    "image_url", payload.getImageBase64()
            );
            var result = fal.queue().submit(baseUrl,
                    QueueSubmitOptions.<JsonObject>builder().input(input).build());

            return result.getRequestId();
        } catch (Exception e) {
            log.error("Failed to submit {} request: {}", serviceName, e.getMessage());
            throw new SubmissionException(serviceName, e);
        }
    }

    @Override
    public ImageResponseDto fetchResult(String requestId) {
        return getFalImageResponse(requestId);
    }
}
