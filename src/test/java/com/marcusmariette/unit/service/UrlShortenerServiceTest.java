package com.marcusmariette.unit.service;
import com.marcusmariette.repository.UrlRepository;
import com.marcusmariette.service.UrlShortenerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UrlShortenerServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlShortenerService urlShortenerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShortenUrl_NewMapping_SavesToRepository() {
        String originalUrl = "https://example.com/page";
        String expectedShortUrlPrefix = "http://short.ly/";

        when(urlRepository.exists(anyString())).thenReturn(false);

        String result = urlShortenerService.shortenUrl(originalUrl);

        // Should start with base URL and have 8-char code
        assertTrue(result.startsWith(expectedShortUrlPrefix));
        assertEquals(8, result.substring(expectedShortUrlPrefix.length()).length());

        // Verify repository save was called
        verify(urlRepository, times(1)).save(anyString(), eq(originalUrl));
    }

    @Test
    void testShortenUrl_ExistingMapping_DoesNotSaveAgain() {
        String originalUrl = "https://example.com/page";

        when(urlRepository.exists(anyString())).thenReturn(true);

        String result = urlShortenerService.shortenUrl(originalUrl);

        // Should still return valid short URL
        assertTrue(result.startsWith("http://short.ly/"));
        verify(urlRepository, never()).save(any(), any());
    }

    @Test
    void testGetOriginalUrl_ReturnsExpectedResult() {
        String shortCode = "abc123XY";
        String originalUrl = "https://example.com/page";

        when(urlRepository.findOriginalUrl(shortCode)).thenReturn(originalUrl);

        String result = urlShortenerService.getOriginalUrl(shortCode);

        assertEquals(originalUrl, result);
        verify(urlRepository).findOriginalUrl(shortCode);
    }

    @Test
    void testGetOriginalUrl_NotFoundReturnsNull() {
        String shortCode = "notfound";

        when(urlRepository.findOriginalUrl(shortCode)).thenReturn(null);

        String result = urlShortenerService.getOriginalUrl(shortCode);

        assertNull(result);
    }
}
