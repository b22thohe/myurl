package com.example.myurl.controller;

import com.example.myurl.entity.ShortUrl;
import com.example.myurl.repository.ShortUrlRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc

public class UrlShortenerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testShortenAndRedirectAndStats() throws Exception {
        // 1. Shorten URL
        String originalUrl = "https://www.example.com";
        String response = mockMvc.perform(post("/api/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(originalUrl)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // Extract short code from the returned JSON
        String shortCode = response.replaceAll(".*\\/([a-zA-Z0-9]+).*", "$1");

        // 2. Test redirect
        ShortUrl shortUrl = shortUrlRepository.findByShortCode(shortCode).orElseThrow();
        mockMvc.perform(get("/" + shortCode))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(shortUrl.getOriginalUrl()));

        // 3. Test stats
        mockMvc.perform(get("/api/stats/" + shortCode))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("clickCount")));
    }
}
