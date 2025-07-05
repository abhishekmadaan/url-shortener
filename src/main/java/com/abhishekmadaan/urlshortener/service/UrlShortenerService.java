package com.abhishekmadaan.urlshortener.service;

import org.springframework.stereotype.Service;

@Service
public class UrlShortenerService {

    public String shortenUrl(String longUrl) {
        //Add logic
        return "shortened-Url";
    }

    public String getLongUrl(String shortUrl) {
        //Add logic
        return "long-url";
    }
}
