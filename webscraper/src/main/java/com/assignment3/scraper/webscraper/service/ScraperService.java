package com.assignment3.scraper.webscraper.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ScraperService {

    private static final String CSV_FILE_PATH = "/Users/kayahir/Desktop/Assignment3/webscraper/src/main/resources/scraped_data.csv";
    private static final int MAX_LINES = 300;

    // Ensuring that writing to the CSV file is thread-safe
    private synchronized void writeToCsv(String url, String content) {
        try (FileWriter fileWriter = new FileWriter(CSV_FILE_PATH, true)) {
            fileWriter.append(url).append(",").append(content.replace("\n", " ")).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String truncateContent(String content) {
        String[] lines = content.split("\n");
        if (lines.length > MAX_LINES) {
            content = String.join("\n", Arrays.copyOfRange(lines, 0, MAX_LINES));
        }
        return content;
    }

    @Async
    public CompletableFuture<String> scrapeAndSave(String url) {
        try {
            Document document = Jsoup.connect(url).timeout(10000).get();
            String content = truncateContent(document.body().text());
            String truncatedUrl = url.length() > 255 ? url.substring(0, 255) : url;

            writeToCsv(truncatedUrl, content);

            return CompletableFuture.completedFuture("Data scraped and saved for URL: " + truncatedUrl);
        } catch (IOException e) {
            return CompletableFuture.completedFuture("Failed to scrape URL: " + url);
        }
    }

    public List<CompletableFuture<String>> scrapeMultipleUrls(List<String> urls) {
        return urls.stream()
                .map(this::scrapeAndSave)
                .collect(Collectors.toList());
    }
}
