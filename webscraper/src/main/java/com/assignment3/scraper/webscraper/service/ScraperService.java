package com.assignment3.scraper.webscraper.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Service
public class ScraperService {

    private final ExecutorService executorService;

    @Autowired
    public ScraperService(ExecutorService executorService) {
        this.executorService = executorService; // Injected from ThreadPoolConfig
    }

    /**
     * Reads URLs from the specified file in resources.
     */
    public List<String> readUrlsFromFile(String fileName) {
        List<String> urls = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/" + fileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                urls.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urls;
    }

    /**
     * Initiates scraping for each URL found in the URLs file and measures the time difference
     * between single-threaded and thread-pooled approaches.
     */
    public void scrapeFromFile() {
        List<String> urls = readUrlsFromFile("urls.txt");

        // Measure time for single-threaded scraping
        long singleThreadStartTime = System.currentTimeMillis();
        for (String url : urls) {
            scrapeSingleThread(url);
        }
        long singleThreadEndTime = System.currentTimeMillis();
        System.out.println("Single-threaded scraping took: " + (singleThreadEndTime - singleThreadStartTime) + " ms");

        // Measure time for thread-pooled scraping
        long threadPoolStartTime = System.currentTimeMillis();
        for (String url : urls) {
            parseAndStoreContent(url); // Using thread pool
        }
        long threadPoolEndTime = System.currentTimeMillis();
        System.out.println("Thread-pooled scraping took: " + (threadPoolEndTime - threadPoolStartTime) + " ms");
    }

    /**
     * Scrapes and stores content from a given URL in a single-threaded manner.
     *
     * @param url the URL to scrape
     */
    private void scrapeSingleThread(String url) {
        try {
            // Log the start of the task
            System.out.println("Starting single-threaded scraping for URL: " + url);

            // Connect and parse the document
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                    .get();

            // Extract content
            StringBuilder content = new StringBuilder();
            content.append("Headings:\n");
            for (Element heading : doc.select("h1, h2, h3, h4, h5, h6")) {
                content.append(heading.tagName()).append(": ").append(heading.text()).append("\n");
            }

            content.append("\nParagraphs:\n");
            for (Element paragraph : doc.select("p")) {
                content.append(paragraph.text()).append("\n");
            }

            content.append("\nList Items:\n");
            for (Element listItem : doc.select("li")) {
                content.append(listItem.text()).append("\n");
            }

            content.append("\nTables:\n");
            for (Element table : doc.select("table")) {
                for (Element row : table.select("tr")) {
                    for (Element cell : row.select("td, th")) {
                        content.append(cell.text()).append("\t");
                    }
                    content.append("\n");
                }
            }

            // Save the extracted content to a CSV file
            saveToCSV(url, content.toString());

            // Log the completion of the task
            System.out.println("Completed single-threaded scraping for URL: " + url);

        } catch (IOException e) {
            System.err.println("Failed to scrape URL: " + url + " - " + e.getMessage());
        }
    }

    /**
     * Parses and stores content from a given URL using the injected thread pool.
     *
     * @param url the URL to scrape
     */
    public void parseAndStoreContent(String url) {
        executorService.submit(() -> {
            // Log the start of the task and the thread handling it
            System.out.println("Starting scraping for URL: " + url + " on thread: " + Thread.currentThread().getName());

            try {
                // Connect and parse the document
                Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                        .get();

                // Extract generic content based on HTML tags
                StringBuilder content = new StringBuilder();
                content.append("Headings:\n");
                for (Element heading : doc.select("h1, h2, h3, h4, h5, h6")) {
                    content.append(heading.tagName()).append(": ").append(heading.text()).append("\n");
                }

                content.append("\nParagraphs:\n");
                for (Element paragraph : doc.select("p")) {
                    content.append(paragraph.text()).append("\n");
                }

                content.append("\nList Items:\n");
                for (Element listItem : doc.select("li")) {
                    content.append(listItem.text()).append("\n");
                }

                content.append("\nTables:\n");
                for (Element table : doc.select("table")) {
                    for (Element row : table.select("tr")) {
                        for (Element cell : row.select("td, th")) {
                            content.append(cell.text()).append("\t");
                        }
                        content.append("\n");
                    }
                }

                // Save the extracted content to a CSV file
                saveToCSV(url, content.toString());

                // Log the completion of the task
                System.out.println("Completed scraping for URL: " + url + " on thread: " + Thread.currentThread().getName());

            } catch (IOException e) {
                System.err.println("Failed to scrape URL: " + url + " - " + e.getMessage());
            }
        });
    }

    /**
     * Saves scraped data to a CSV file.
     *
     * @param url the URL of the scraped page
     * @param content the scraped content
     */
    public void saveToCSV(String url, String content) {
        boolean fileExists = new File("scraped_data.csv").exists();

        try (FileWriter writer = new FileWriter("scraped_data.csv", true)) {
            // Add headers if the file does not exist
            if (!fileExists) {
                writer.append("URL,Content\n");
            }

            // Write the URL and content, escaping double quotes in content
            writer.append(url).append(",\"").append(content.replace("\"", "\"\"")).append("\"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}