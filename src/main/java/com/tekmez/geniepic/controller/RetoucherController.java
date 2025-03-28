package com.tekmez.geniepic.controller;

import com.tekmez.geniepic.interfaces.IRetourcherService;
import com.tekmez.geniepic.model.dto.ImageReqDto;
import com.tekmez.geniepic.model.dto.ImageResponseDto;
import com.tekmez.geniepic.service.falAi.QueueService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/retoucher")
public class RetoucherController {
    private final QueueService<IRetourcherService> queueService;

    public RetoucherController(QueueService<IRetourcherService> queueService) {
        this.queueService = queueService;
    }
    
    @PostMapping
    public ResponseEntity<ImageResponseDto> retouchImage(@Valid @RequestBody ImageReqDto payload) {
        ImageResponseDto responseUrl = queueService.processRequest(payload);
        return ResponseEntity.ok(responseUrl);
    }
} 