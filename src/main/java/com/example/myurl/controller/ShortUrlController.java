package com.example.myurl.controller;

import com.example.myurl.entity.ShortUrl;
import com.example.myurl.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.util.Optional;

@RestController
public class ShortUrlController {
    @Autowired
    private ShortUrlService shortUrlService;

    // POST endpoint to create a short URL
    @PostMapping("/api/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody String originalUrl) {
        ShortUrl shortUrl = shortUrlService.createShortUrl(originalUrl);
        String shortUrlString = "http://localhost:8080/" + shortUrl.getShortCode();
        return ResponseEntity.created(URI.create(shortUrlString))
                .body("{\"shortUrl\": \"" + shortUrlString + "\"}");
    }

    // GET endpoint for redirecting
    @GetMapping("/{shortCode}")
    public RedirectView redirectToOriginal(@PathVariable String shortCode) {
        Optional<ShortUrl> urlOpt = shortUrlService.getByShortCode(shortCode);
        if (urlOpt.isPresent()) {
            ShortUrl shortUrl = urlOpt.get();
            shortUrlService.incrementClickCount(shortUrl);
            return new RedirectView(shortUrl.getOriginalUrl());
        } else {
            // You can customize this as needed (maybe a custom error page)
            return new RedirectView("/not-found");
        }
    }

    // (Optional) GET endpoint for statistics
    @GetMapping("/api/stats/{shortCode}")
    public ResponseEntity<?> getStats(@PathVariable String shortCode) {
        Optional<ShortUrl> urlOpt = shortUrlService.getByShortCode(shortCode);
        if (urlOpt.isPresent()) {
            ShortUrl shortUrl = urlOpt.get();
            return ResponseEntity.ok("{\"originalUrl\": \"" + shortUrl.getOriginalUrl() +
                    "\", \"clickCount\": " + shortUrl.getClickCount() + "}");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
