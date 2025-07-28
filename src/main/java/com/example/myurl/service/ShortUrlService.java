package com.example.myurl.service;

import com.example.myurl.entity.ShortUrl;
import com.example.myurl.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class ShortUrlService {

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    // Generate shortcode by random
    private String generateShortCode() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int length = 5;
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    // Create new ShortUrl entry
    public ShortUrl createShortUrl(String originalUrl) {
        String shortCode;
        // Do-While-loop ensures that generated code is unique
        do {
            shortCode = generateShortCode();
        } while (shortUrlRepository.findByShortCode(shortCode).isPresent());

        ShortUrl shortUrl = new ShortUrl(originalUrl, shortCode);
        return shortUrlRepository.save(shortUrl);
    }

    // Find by shortCode
    public Optional<ShortUrl> getByShortCode(String shortCode) {
        return shortUrlRepository.findByShortCode(shortCode);
    }

    // Increment click count
    public void incrementClickCount(ShortUrl shortUrl) {
        shortUrl.setClickCount(shortUrl.getClickCount() + 1);
        shortUrlRepository.save(shortUrl);
    }
}
