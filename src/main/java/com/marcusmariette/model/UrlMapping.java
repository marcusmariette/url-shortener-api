package com.marcusmariette.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlMapping {
    @NotBlank(message = "Original URL must not be empty")
    private String originalUrl;
    private String shortUrl;
}
