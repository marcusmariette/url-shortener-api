package com.marcusmariette.unit.util;

import org.junit.jupiter.api.Test;

import static com.marcusmariette.util.UrlShortenerUtil.extractShortCode;
import static com.marcusmariette.util.UrlShortenerUtil.generateShortCode;
import static org.junit.jupiter.api.Assertions.*;

class UrlShortenerUtilTest {

    @Test
    void testGenerateShortCodeReturns8Chars() {
        String originalUrl = "https://example.com/some/long/url";
        String shortCode = generateShortCode(originalUrl);

        assertNotNull(shortCode);
        assertEquals(8, shortCode.length());
    }

    @Test
    void testGenerateShortCodeDeterministicOutput() {
        String url = "https://example.com/page";
        String shortCode1 = generateShortCode(url);
        String shortCode2 = generateShortCode(url);

        assertEquals(shortCode1, shortCode2); // Should always return the same hash-based short code
    }

    @Test
    void testGenerateShortCodeUniquenessForDifferentInputs() {
        String url1 = "https://example.com/page1";
        String url2 = "https://example.com/page2";

        String shortCode1 = generateShortCode(url1);
        String shortCode2 = generateShortCode(url2);

        assertNotEquals(shortCode1, shortCode2); // Expect different codes for different URLs
    }

    @Test
    void testExtractShortCodeValid() {
        String shortUrl = "http://short.ly/a1B2c3Xy";
        String expected = "a1B2c3Xy";

        String result = extractShortCode(shortUrl);
        assertEquals(expected, result);
    }

    @Test
    void testExtractShortCodeInvalid() {
        String invalidUrl = "http://other.ly/a1B2c3Xy";

        String result = extractShortCode(invalidUrl);
        assertNull(result); // Should return null for unexpected base URL
    }

    @Test
    void testExtractShortCodeEdgeCase() {
        String baseOnly = "http://short.ly/";

        String result = extractShortCode(baseOnly);
        assertEquals("", result); // Valid, just empty path
    }
}
