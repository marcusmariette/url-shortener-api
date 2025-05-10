package com.marcusmariette.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UrlShortenerUtil {

    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    // Hash the original URL using SHA-256 ensures safe and non-sequential short URLS
    public static String generateShortCode(String originalUrl) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(originalUrl.getBytes(StandardCharsets.UTF_8));

            // Convert the hash to base62 and take the first 8 characters
            return encodeBase62(hash).substring(0, 8);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    // Encoder method Base62
    private static String encodeBase62(byte[] input) {
        StringBuilder sb = new StringBuilder();
        BigInteger number = new BigInteger(1, input);

        while (number.compareTo(BigInteger.ZERO) > 0) {
            int remainder = number.mod(BigInteger.valueOf(62)).intValue();
            sb.append(BASE62.charAt(remainder));
            number = number.divide(BigInteger.valueOf(62));
        }

        return sb.reverse().toString();
    }

    // Helper method to extract the short code from the full short URL
    public static String extractShortCode(String shortUrl) {
        // Assuming base short URL is "http://short.ly/"
        String baseShortUrl = "http://short.ly/";
        if (shortUrl.startsWith(baseShortUrl)) {
            return shortUrl.substring(baseShortUrl.length());
        }
        return null;  // Invalid short URL format
    }
}
