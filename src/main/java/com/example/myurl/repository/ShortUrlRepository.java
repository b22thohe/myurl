package com.example.myurl.repository;

import com.example.myurl.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    // Query: Find by shortCode
    Optional<ShortUrl> findByShortCode(String shortCode);

    // Add more queries here

}
