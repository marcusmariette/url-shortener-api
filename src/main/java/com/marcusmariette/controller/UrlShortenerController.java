package com.marcusmariette.controller;

import com.marcusmariette.error.ErrorResponse;
import com.marcusmariette.model.UrlMapping;
import com.marcusmariette.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

import static com.marcusmariette.util.UrlShortenerUtil.extractShortCode;
import static com.marcusmariette.util.UrlValidationUtil.isValidOriginalUrl;
import static com.marcusmariette.util.UrlValidationUtil.isValidShortUrl;

@RestController
@RequestMapping("/api")
@Validated
@AllArgsConstructor
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody @Valid UrlMapping urlMapping) {
        String originalUrl = urlMapping.getOriginalUrl();

        if (!isValidOriginalUrl(originalUrl)) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatusCode.valueOf(400).toString(),  "Invalid original URL format"));
        }

        String shortUrl = urlShortenerService.shortenUrl(originalUrl);
        urlMapping.setShortUrl(shortUrl);
        return ResponseEntity.ok(urlMapping);
    }

    @GetMapping("/redirect")
    public ResponseEntity<?> redirectToOriginal(@RequestParam String shortUrl, HttpServletResponse response) throws IOException {
        if (!isValidShortUrl(shortUrl)) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatusCode.valueOf(400).toString(),  "Invalid short URL format"));
        }

        String shortCode = extractShortCode(shortUrl);
        String originalUrl = urlShortenerService.getOriginalUrl(shortCode);
        if (Objects.nonNull(originalUrl)) {
            response.sendRedirect(originalUrl);
            return ResponseEntity.status(302).build();
        }

        return ResponseEntity.status(404)
                .body(new ErrorResponse(HttpStatusCode.valueOf(404).toString(),"Short URL not found"));
    }
}
