package com.bargain.executableSpecifications.service;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SharedStorage {

    private Map<String, Object> clipboard;
    private String latestKey;

    public SharedStorage() {
        clipboard = new ConcurrentHashMap<>();
    }

    public Object get(String key) {
        return clipboard.get(key);
    }

    public void set(String key, Object value) {
        clipboard.put(key, value);
        latestKey = key;
    }

    public Object getLatestResource() {
        Object value = clipboard.get(latestKey);
        if (value == null) {
            throw new IllegalArgumentException(String.format("There is no value for key %s", latestKey));
        }

        return value;
    }

    public void clear() {
        this.clipboard.clear();
    }
}
