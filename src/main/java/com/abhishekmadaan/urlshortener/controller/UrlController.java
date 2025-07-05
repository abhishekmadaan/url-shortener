package com.abhishekmadaan.urlshortener.controller;

import com.abhishekmadaan.urlshortener.service.UrlKeyGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UrlController {
    @Autowired
    UrlKeyGeneratorService urlKeyGeneratorService;

    @PostMapping("/shorten")
    public String shortenURL(@RequestBody String longUrl) {
        //call service
        return "shortUrl";
    }

    @GetMapping("/{shortUrl}")
    public String getLongUrl(@PathVariable String shortUrl) {
        //call service
        return "longUrl";
    }
}
