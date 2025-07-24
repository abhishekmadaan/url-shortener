package com.abhishekmadaan.urlshortener.service;

import com.abhishekmadaan.urlshortener.model.UrlMapping;
import com.abhishekmadaan.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UrlShortenerService {

    private final UrlRepository urlRepository;

    public UrlShortenerService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenUrl(String longUrl) {
        Optional<UrlMapping> shortUrlExisting = urlRepository.findByOriginalUrl(longUrl);
        if(shortUrlExisting.isPresent()) {
            return shortUrlExisting.get().getShortUrl();
        }
        String shortUrl = generateShortKey(longUrl);

        UrlMapping urlMapping = UrlMapping.builder()
                .originalUrl(longUrl)
                .shortUrl(shortUrl)
                .createdAt(LocalDateTime.now())
                .build();

        urlRepository.save(urlMapping);
        return shortUrl;
    }

    public String getLongUrl(String shortUrl) {
        Optional<UrlMapping> longUrlExisting = urlRepository.findByShortUrl(shortUrl);
        return longUrlExisting.map(UrlMapping::getOriginalUrl).orElse(null);
    }

    private String generateShortKey(String longUrl) {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
