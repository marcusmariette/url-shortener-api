package com.marcusmariette.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UrlRepository {

    private final Map<String, String> store = new ConcurrentHashMap<>();

    public void save(String shortCode, String originalUrl) {
        store.put(shortCode, originalUrl);
    }

    public String findOriginalUrl(String shortCode) {
        return store.get(shortCode);
    }

    public boolean exists(String shortCode) {
        return store.containsKey(shortCode);
    }
}
