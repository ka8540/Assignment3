package com.assignment3.scraper.webscraper.controller;

import com.assignment3.scraper.webscraper.model.URLRequest;
import com.assignment3.scraper.webscraper.service.ScraperService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scrape")
public class ScraperController {

    private final ScraperService scraperService;

    @Autowired
    public ScraperController(ScraperService scraperService) {
        this.scraperService = scraperService;
    }

    /**
     * Endpoint to initiate scraping of a given URL.
     *
     * @param @ScrapedData the request containing the URL to be scraped
     * @return ResponseEntity with status and message
     */
    @PostMapping("")
    public ResponseEntity<String> scrapeURL(@RequestBody URLRequest urlRequest) {
        System.out.println("Method reached"); // Basic logging
        try {
            scraperService.parseAndStoreContent(urlRequest.getUrl());
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Scraping initiated for URL: " + urlRequest.getUrl());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to scrape URL: " + urlRequest.getUrl());
        }
    }
}