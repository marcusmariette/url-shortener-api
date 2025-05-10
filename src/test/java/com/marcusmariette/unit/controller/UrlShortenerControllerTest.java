package com.marcusmariette.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcusmariette.controller.UrlShortenerController;
import com.marcusmariette.model.UrlMapping;
import com.marcusmariette.service.UrlShortenerService;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UrlShortenerController.class)
class UrlShortenerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlShortenerService urlShortenerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testShortenUrl_ValidRequest_ReturnsShortenedUrl() throws Exception {
        UrlMapping mapping = new UrlMapping();
        mapping.setOriginalUrl("https://example.com/page");

        when(urlShortenerService.shortenUrl(anyString()))
                .thenReturn("http://short.ly/abc123XY");

        mockMvc.perform(post("/api/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mapping)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalUrl").value("https://example.com/page"))
                .andExpect(jsonPath("$.shortUrl").value("http://short.ly/abc123XY"));
    }

    @Test
    void testShortenUrl_InvalidOriginalUrl_ReturnsBadRequest() throws Exception {
        UrlMapping mapping = new UrlMapping();
        mapping.setOriginalUrl("bad_url");

        mockMvc.perform(post("/api/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mapping)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid original URL format"));
    }

    @Test
    void testRedirectToOriginal_ValidShortUrl_Redirects() throws Exception {
        when(urlShortenerService.getOriginalUrl("abc123XY"))
                .thenReturn("https://example.com/page");

        mockMvc.perform(get("/api/redirect")
                        .param("shortUrl", "http://short.ly/abc123XY"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "https://example.com/page"));
    }

    @Test
    void testRedirectToOriginal_InvalidShortUrlFormat_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/redirect")
                        .param("shortUrl", "invalid-short-url"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid short URL format"));
    }

    @Test
    void testRedirectToOriginal_ShortUrlNotFound_Returns404() throws Exception {
        when(urlShortenerService.getOriginalUrl("notfound"))
                .thenReturn(null);

        mockMvc.perform(get("/api/redirect")
                        .param("shortUrl", "http://short.ly/notfound"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Short URL not found"));
    }
}
