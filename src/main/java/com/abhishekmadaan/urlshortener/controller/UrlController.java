package com.abhishekmadaan.urlshortener.controller;

import com.abhishekmadaan.urlshortener.dto.OriginalUrlResponse;
import com.abhishekmadaan.urlshortener.dto.ShortenUrlRequest;
import com.abhishekmadaan.urlshortener.dto.ShortenUrlResponse;
import com.abhishekmadaan.urlshortener.service.UrlShortenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UrlController {

    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);
    private final UrlShortenerService urlShortenerService;

    public UrlController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortenUrlResponse> shortenURL(@RequestBody ShortenUrlRequest shortenUrlRequest) {
        logger.info("Received request to shorten URL.");
        String longUrl = shortenUrlRequest.getOriginalUrl();
        if (longUrl == null || longUrl.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        String shortUrl = urlShortenerService.shortenUrl(longUrl);
        logger.info("Generated short URL: {} for original URL: {}", shortUrl, longUrl);
        return ResponseEntity.ok(new ShortenUrlResponse(shortUrl));
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<OriginalUrlResponse> getLongUrl(@PathVariable String shortUrl) {
        String originalUrl = urlShortenerService.getLongUrl(shortUrl);
        logger.info("Received request to get original URL.");

        if (originalUrl == null) {
            return ResponseEntity.notFound().build();
        }
        logger.info("Original url is {}", originalUrl);
        return ResponseEntity.ok(new OriginalUrlResponse(originalUrl));
    }

    @DeleteMapping("/{shortUrl}")
    public ResponseEntity<Void> deleteUrl(@PathVariable String shortUrl) {
        logger.info("Received request to delete URL from database.");
        boolean deleted = urlShortenerService.deleteShortUrl(shortUrl);
        if(deleted) logger.info("Deleted the URL.");
        else logger.info("URL doesn't exist.");
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
