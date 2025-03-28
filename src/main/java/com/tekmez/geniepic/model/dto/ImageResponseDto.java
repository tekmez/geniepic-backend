package com.tekmez.geniepic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImageResponseDto {
    private String requestId;
    private String resultUrl;
}
