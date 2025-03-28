package com.tekmez.geniepic.interfaces;

import com.tekmez.geniepic.model.dto.ImageReqDto;
import com.tekmez.geniepic.model.dto.ImageResponseDto;

public interface IAIService {
    String submitRequest(ImageReqDto input); // AI API'ye istek atar
    String checkStatus(String requestId); // AI API'den status çeker
    ImageResponseDto fetchResult(String requestId); // AI API'den sonucu çeker
}