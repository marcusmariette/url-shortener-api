package com.marcusmariette.unit.util;
import org.junit.jupiter.api.Test;

import static com.marcusmariette.util.UrlValidationUtil.isValidOriginalUrl;
import static com.marcusmariette.util.UrlValidationUtil.isValidShortUrl;
import static org.junit.jupiter.api.Assertions.*;

class UrlValidationUtilTest {

    @Test
    void testIsValidOriginalUrl_ValidUrlWithHttp() {
        String validUrl = "https://www.example.com";
        assertTrue(isValidOriginalUrl(validUrl));
    }

    @Test
    void testIsValidOriginalUrl_ValidUrlWithHttps() {
        String validUrl = "http://example.com";
        assertTrue(isValidOriginalUrl(validUrl));
    }

    @Test
    void testIsValidOriginalUrl_ValidUrlWithoutProtocol() {
        String validUrl = "example.com";
        assertTrue(isValidOriginalUrl(validUrl));
    }

    @Test
    void testIsValidOriginalUrl_InvalidUrlWithSpecialCharacters() {
        String invalidUrl = "https://example@com/page!";
        assertFalse(isValidOriginalUrl(invalidUrl));
    }

    @Test
    void testIsValidOriginalUrl_EmptyUrl() {
        String emptyUrl = "";
        assertFalse(isValidOriginalUrl(emptyUrl));
    }

    @Test
    void testIsValidOriginalUrl_NullUrl() {
        assertFalse(isValidOriginalUrl(null));
    }

    @Test
    void testIsValidShortUrl_ValidShortUrl() {
        String validShortUrl = "http://short.ly/a1B2c3Xy";
        assertTrue(isValidShortUrl(validShortUrl));
    }

    @Test
    void testIsValidShortUrl_InvalidShortUrl_WrongBaseUrl() {
        String invalidShortUrl = "http://example.com/a1B2c3Xy";
        assertFalse(isValidShortUrl(invalidShortUrl));
    }

    @Test
    void testIsValidShortUrl_InvalidShortUrl_WrongLength() {
        String invalidShortUrl = "http://short.ly/abc";
        assertFalse(isValidShortUrl(invalidShortUrl));
    }

    @Test
    void testIsValidShortUrl_EmptyShortUrl() {
        String emptyShortUrl = "";
        assertFalse(isValidShortUrl(emptyShortUrl));
    }

    @Test
    void testIsValidShortUrl_NullShortUrl() {
        assertFalse(isValidShortUrl(null));
    }
}