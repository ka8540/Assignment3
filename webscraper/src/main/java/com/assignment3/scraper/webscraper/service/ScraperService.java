package com.assignment3.scraper.webscraper.service;

import com.assignment3.scraper.webscraper.model.ScrapedData;
import com.assignment3.scraper.webscraper.repository.ScrapedDataRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ScraperService {

    private final ScrapedDataRepository scrapedDataRepository;
    private final ExecutorService executorService;

    @Autowired
    public ScraperService(ScrapedDataRepository scrapedDataRepository) {
        this.scrapedDataRepository = scrapedDataRepository;
        this.executorService = Executors.newFixedThreadPool(10); // Pool size of 10 threads
    }

    /**
     * Parses and stores content from a given URL using a thread pool for concurrent scraping.
     *
     * @param url the URL to scrape
     */
    public void parseAndStoreContent(String url) {
        executorService.submit(() -> {

            try {
                // Connect and parse the document
                Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                        .get();

                // Extract content
                String content = doc.body().text();

                // Create and save ScrapedData entity
                ScrapedData scrapedData = new ScrapedData();
                scrapedData.setUrl(url);
                scrapedData.setContent(content);
                scrapedDataRepository.save(scrapedData);

                System.out.println("Successfully scraped and stored content for URL: " + url);
            } catch (IOException e) {
                System.err.println("Failed to scrape URL: " + url + " - " + e.getMessage());
            }
        });
    }
}