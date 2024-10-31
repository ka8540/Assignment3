package com.assignment3.scraper.webscraper.controller;

import com.assignment3.scraper.webscraper.dto.ScrapedDataDTO;
import com.assignment3.scraper.webscraper.service.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/scraper")
public class ScraperController {

    @Autowired
    private ScraperService scraperService;

    // POST endpoint to trigger scraping for a single URL
    @PostMapping("/scrape")
    public CompletableFuture<String> scrapeUrl(@RequestBody Map<String, String> requestBody) {
        String url = requestBody.get("url");
        return scraperService.scrapeAndSave(url);
    }

    // POST endpoint to trigger scraping for multiple URLs concurrently
    @PostMapping("/scrapeAll")
    public CompletableFuture<List<String>> scrapeMultipleUrls(@RequestBody List<String> urls) {
        List<CompletableFuture<String>> scrapeFutures = scraperService.scrapeMultipleUrls(urls);

        // Wait for all scrape operations to complete and collect results
        return CompletableFuture.allOf(scrapeFutures.toArray(new CompletableFuture[0]))
                .thenApply(v -> scrapeFutures.stream()
                        .map(CompletableFuture::join) // Collect results from each CompletableFuture
                        .collect(Collectors.toList()));
    }

    // Endpoint to fetch all scraped data with truncated fields
    @GetMapping("/data")
    public List<ScrapedDataDTO> getAllData() {
        return scraperService.getAllScrapedData();
    }
}
