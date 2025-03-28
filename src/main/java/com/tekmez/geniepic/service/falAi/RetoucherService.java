package com.tekmez.geniepic.service.falAi;

import ai.fal.client.queue.QueueSubmitOptions;
import com.google.gson.JsonObject;
import com.tekmez.geniepic.exception.SubmissionException;
import com.tekmez.geniepic.interfaces.IRetourcherService;
import com.tekmez.geniepic.model.dto.ImageReqDto;
import com.tekmez.geniepic.model.dto.ImageResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@Log4j2
public class RetoucherService extends BaseFalAIService implements IRetourcherService {
    public RetoucherService() {
        super("Retoucher", "fal-ai/retoucher");
    }

    @Override
    public String submitRequest(ImageReqDto payload) {
        try {
            var input = Map.of(
                    "image_url",  payload.getImageBase64()
            );
            var result = fal.queue().submit(baseUrl,
                    QueueSubmitOptions.<JsonObject>builder()
                            .input(input)
                            .build()
            );
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
