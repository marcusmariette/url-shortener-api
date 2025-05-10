package com.marcusmariette.util;

import java.util.Objects;
import java.util.regex.Pattern;

public class UrlValidationUtil {

    private static final String ORIGINAL_URL_REGEX = "^(https?://)?(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+(/\\S*)?$";
    private static final String SHORT_URL_REGEX = "^http://short\\.ly/[a-zA-Z0-9]{8}$";

    private static final Pattern ORIGINAL_URL_PATTERN = Pattern.compile(ORIGINAL_URL_REGEX);
    private static final Pattern SHORT_URL_PATTERN = Pattern.compile(SHORT_URL_REGEX);

    public static boolean isValidOriginalUrl(String url) {
        return Objects.nonNull(url) && ORIGINAL_URL_PATTERN.matcher(url).matches();
    }

    public static boolean isValidShortUrl(String url) {
        return Objects.nonNull(url) && SHORT_URL_PATTERN.matcher(url).matches();
    }
}