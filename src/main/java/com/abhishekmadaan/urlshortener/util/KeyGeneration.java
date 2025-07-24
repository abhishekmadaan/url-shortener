package com.abhishekmadaan.urlshortener.util;

import com.abhishekmadaan.urlshortener.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.zip.CRC32;

@Component
public class KeyGeneration {

    private static final Logger logger = LoggerFactory.getLogger(KeyGeneration.class);

    private final UrlRepository urlRepository;

    public KeyGeneration(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String generateShortKey(String longUrl) {
        logger.info("Generating short key for URL: {}", longUrl);

        CRC32 crc = new CRC32();
        crc.update(longUrl.getBytes());
        long checksum = crc.getValue();
        logger.debug("CRC32 checksum (decimal): {}", checksum);

        String baseKey = Long.toString(checksum, 36); // Step 4

        if (baseKey.length() > 7) {
            baseKey = baseKey.substring(0, 7); // Step 5
        }

        String shortKey = baseKey;
        int counter = 0;

        while (urlRepository.findByShortUrl(shortKey).isPresent()) {
            counter++;
            shortKey = baseKey + counter;
            logger.warn("Collision detected! Trying new key: {}", shortKey);
        }

        logger.info("Final generated short key: {}", shortKey);
        return shortKey;
    }
}
