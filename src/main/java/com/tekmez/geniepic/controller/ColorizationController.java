package com.tekmez.geniepic.controller;

import com.tekmez.geniepic.interfaces.IColorizationService;
import com.tekmez.geniepic.model.dto.ImageReqDto;
import com.tekmez.geniepic.model.dto.ImageResponseDto;
import com.tekmez.geniepic.service.falAi.QueueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/colorization")
public class ColorizationController {
    private final QueueService<IColorizationService> queueService;

    @Autowired
    public ColorizationController(QueueService<IColorizationService> queueService) {
        this.queueService = queueService;
    }

    @PostMapping
    public ResponseEntity<ImageResponseDto> colorize(@Valid @RequestBody ImageReqDto payload) {
        ImageResponseDto responseUrl = queueService.processRequest(payload);
        return ResponseEntity.ok(responseUrl);
    }
}
