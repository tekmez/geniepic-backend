package com.tekmez.geniepic.controller;

import com.tekmez.geniepic.interfaces.IBackgroundRemoverService;
import com.tekmez.geniepic.model.dto.ImageReqDto;
import com.tekmez.geniepic.model.dto.ImageResponseDto;
import com.tekmez.geniepic.service.falAi.QueueService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eraser")
public class BackgroundController {
    private final QueueService<IBackgroundRemoverService> queueService;

    public BackgroundController(QueueService<IBackgroundRemoverService> queueService) {
        this.queueService = queueService;
    }
    
    @PostMapping
    public ResponseEntity<ImageResponseDto> removerBackground(@Valid @RequestBody ImageReqDto payload) {
        ImageResponseDto responseUrl = queueService.processRequest(payload);
        return  ResponseEntity.ok(responseUrl);
    }
}
