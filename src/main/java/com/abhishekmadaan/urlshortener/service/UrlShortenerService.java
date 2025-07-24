package com.abhishekmadaan.urlshortener.service;

import com.abhishekmadaan.urlshortener.model.UrlMapping;
import com.abhishekmadaan.urlshortener.repository.UrlRepository;
import com.abhishekmadaan.urlshortener.util.KeyGeneration;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlShortenerService {

    private final UrlRepository urlRepository;
    private final KeyGeneration keyGeneration;

    public UrlShortenerService(UrlRepository urlRepository, KeyGeneration keyGeneration) {
        this.urlRepository = urlRepository;
        this.keyGeneration = keyGeneration;
    }

    public String shortenUrl(String longUrl) {
        Optional<UrlMapping> shortUrlExisting = urlRepository.findByOriginalUrl(longUrl);
        if(shortUrlExisting.isPresent()) {
            return shortUrlExisting.get().getShortUrl();
        }
        String shortUrl = keyGeneration.generateShortKey(longUrl);

        UrlMapping urlMapping = UrlMapping.builder()
                .originalUrl(longUrl)
                .shortUrl(shortUrl)
                .createdAt(LocalDateTime.now())
                .build();

        urlRepository.save(urlMapping);
        return shortUrl;
    }

    @Cacheable(value = "urlCache", key = "#shortUrl")
    public String getLongUrl(String shortUrl) {
        System.out.println("Cache MISS — retrieving from DB: " + shortUrl);
        Optional<UrlMapping> longUrlExisting = urlRepository.findByShortUrl(shortUrl);
        return longUrlExisting.map(UrlMapping::getOriginalUrl).orElse(null);
    }

    @Transactional
    @CacheEvict(value = "urlCache", key = "#shortUrl")
    public boolean deleteShortUrl(String shortUrl) {
        Optional<UrlMapping> existing = urlRepository.findByShortUrl(shortUrl);
        if (existing.isPresent()) {
            urlRepository.deleteByShortUrl(shortUrl);
            return true;
        }
        return false;
    }
}
