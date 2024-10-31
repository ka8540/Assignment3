package com.assignment3.scraper.webscraper.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

@Service
public class UrlFileProcessor {

    private static final String API_ENDPOINT = "http://localhost:8080/api/scraper/scrape";

    // Method to process URLs from a file and send each as a POST request to the API
    public void processUrlsFromFile(String filePath) {
        HttpClient client = HttpClient.newHttpClient();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String url;
            while ((url = br.readLine()) != null) {
                sendPostRequest(client, url);
            }
        } catch (IOException e) {
            System.err.println("Error reading URLs from file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to send a POST request for each URL
    private void sendPostRequest(HttpClient client, String url) {
        String jsonPayload = "{ \"url\": \"" + url + "\" }";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_ENDPOINT))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> System.out.println("Response for URL " + url + ": " + response))
                .exceptionally(ex -> {
                    System.err.println("Failed to send request for URL: " + url);
                    ex.printStackTrace();
                    return null;
                });
    }
}
