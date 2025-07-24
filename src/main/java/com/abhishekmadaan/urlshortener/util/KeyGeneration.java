package com.abhishekmadaan.urlshortener.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class KeyGeneration {
    public String generateShortKey(String longUrl) {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
