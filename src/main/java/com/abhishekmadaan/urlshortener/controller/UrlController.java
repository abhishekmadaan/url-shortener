package com.abhishekmadaan.urlshortener.controller;

import com.abhishekmadaan.urlshortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UrlController {
    @Autowired
    private final UrlShortenerService urlShortenerService;

    public UrlController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/shorten")
    public String shortenURL(@RequestBody String longUrl) {
        return urlShortenerService.shortenUrl(longUrl);
    }

    @GetMapping("/{shortUrl}")
    public String getLongUrl(@PathVariable String shortUrl) {
        return urlShortenerService.getLongUrl(shortUrl);
    }
}
