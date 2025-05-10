package com.marcusmariette.service;

import com.marcusmariette.repository.UrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.marcusmariette.util.UrlShortenerUtil.generateShortCode;

@Service
@AllArgsConstructor
public class UrlShortenerService {
    private static final String BASE_URL = "http://short.ly/";

    @Autowired
    private UrlRepository urlRepository;

    public String shortenUrl(String originalUrl) {
        String shortCode = generateShortCode(originalUrl);

        // If already saved, skip saving again
        if (!urlRepository.exists(shortCode)) {
            urlRepository.save(shortCode, originalUrl);
        }

        return BASE_URL + shortCode;
    }

    public String getOriginalUrl(String shortCode) {
        return urlRepository.findOriginalUrl(shortCode);
    }
}
