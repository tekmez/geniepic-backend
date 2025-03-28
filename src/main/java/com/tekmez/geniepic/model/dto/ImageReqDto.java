package com.tekmez.geniepic.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageReqDto {
    @NotNull(message = "image data cannot be null")
    @NotBlank(message = "image data cannot be blank")
    private String imageBase64;

    private String mask_url;
}
