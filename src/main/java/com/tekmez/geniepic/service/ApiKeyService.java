package com.tekmez.geniepic.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
public class ApiKeyService {

    private final String falKey;
    private final String replicateKey;

    public ApiKeyService(
            @Value("${fal.key}") String falKey,
            @Value("${replicate.key}") String replicateKey
    ) {
        this.falKey = falKey;
        this.replicateKey = replicateKey;
    }

}
