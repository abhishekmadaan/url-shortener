package com.abhishekmadaan.urlshortener;

import com.abhishekmadaan.urlshortener.model.UrlMapping;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class UrlshortenerApplicationTests {

    public static void main(String[] args) {
        UrlMapping urlMapping = UrlMapping.builder()
                .originalUrl("https://example.com")
                .shortUrl("abcd1234")
                .createdAt(LocalDateTime.now())
                .build();

        System.out.println(urlMapping.getShortUrl());
    }

}
