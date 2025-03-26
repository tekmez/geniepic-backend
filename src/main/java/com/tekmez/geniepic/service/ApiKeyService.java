package com.tekmez.geniepic.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Getter
public class ApiKeyService {

    private static final Logger logger = LoggerFactory.getLogger(ApiKeyService.class);
    private final String falKey;
    private final String replicateKey;

    public ApiKeyService(
            @Value("${fal.key}") String falKey,
            @Value("${replicate.key}") String replicateKey
    ) {
        logger.info("FAL_KEY: {}", falKey);
        logger.info("REPLICATE_KEY: {}", replicateKey);
        this.falKey = falKey;
        this.replicateKey = replicateKey;
    }

}
