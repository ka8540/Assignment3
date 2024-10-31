package com.assignment3.scraper.webscraper.service;

import com.assignment3.scraper.webscraper.dto.ScrapedDataDTO;
import com.assignment3.scraper.webscraper.model.ScrapedData;
import com.assignment3.scraper.webscraper.repository.ScrapedDataRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ScraperService {

    @Autowired
    private ScrapedDataRepository scrapedDataRepository;

    private static final int MAX_LINES = 300;

    // Method to limit content to a maximum of 300 lines
    private String truncateContent(String content) {
        String[] lines = content.split("\n");
        if (lines.length <= MAX_LINES) {
            return content;
        }
        return String.join("\n", Arrays.copyOfRange(lines, 0, MAX_LINES));
    }

    @Async
    public CompletableFuture<String> scrapeAndSave(String url) {
        try {
            Document document = Jsoup.connect(url).timeout(10000).get(); // Set a timeout for each request
            String content = truncateContent(document.body().text());

            String truncatedUrl = url.length() > 255 ? url.substring(0, 255) : url;

            ScrapedData data = new ScrapedData();
            data.setUrl(truncatedUrl);
            data.setContent(content);
            scrapedDataRepository.save(data);

            return CompletableFuture.completedFuture("Data scraped and saved for URL: " + truncatedUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture("Failed to scrape URL: " + url);
        }
    }

    // Method to scrape multiple URLs in parallel
    public List<CompletableFuture<String>> scrapeMultipleUrls(List<String> urls) {
        return urls.stream()
                .map(this::scrapeAndSave) // Trigger parallel scraping for each URL
                .collect(Collectors.toList());
    }

    // Method to retrieve all scraped data with limited URL and content length
    public List<ScrapedDataDTO> getAllScrapedData() {
        return scrapedDataRepository.findAll().stream()
                .map(data -> new ScrapedDataDTO(
                        data.getUrl().length() > 10 ? data.getUrl().substring(0, 10) : data.getUrl(),
                        data.getContent().length() > 100 ? data.getContent().substring(0, 100) : data.getContent()))
                .collect(Collectors.toList());
    }
}
