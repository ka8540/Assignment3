package com.assignment3.scraper.webscraper.model;

public class URLRequest {
    private String url;

    // Default constructor
    public URLRequest() {}

    // Constructor with URL parameter
    public URLRequest(String url) {
        this.url = url;
    }

    // Getter and Setter
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

