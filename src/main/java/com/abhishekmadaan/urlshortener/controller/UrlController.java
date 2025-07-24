package com.abhishekmadaan.urlshortener.controller;

import com.abhishekmadaan.urlshortener.dto.ShortenUrlRequest;
import com.abhishekmadaan.urlshortener.dto.ShortenUrlResponse;
import com.abhishekmadaan.urlshortener.service.UrlShortenerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UrlController {

    private final UrlShortenerService urlShortenerService;

    public UrlController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortenUrlResponse> shortenURL(@RequestBody ShortenUrlRequest shortenUrlRequest) {
        String longUrl = shortenUrlRequest.getOriginalUrl();
        if (longUrl == null || longUrl.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        String shortUrl = urlShortenerService.shortenUrl(longUrl);
        return ResponseEntity.ok(new ShortenUrlResponse(shortUrl));
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> getLongUrl(@PathVariable String shortUrl) {
        String originalUrl = urlShortenerService.getLongUrl(shortUrl);

        if (originalUrl == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(originalUrl);
    }
}
