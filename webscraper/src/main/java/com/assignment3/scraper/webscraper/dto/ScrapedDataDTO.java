package com.assignment3.scraper.webscraper.dto;

public class ScrapedDataDTO {
    private String url;
    private String content;

    public ScrapedDataDTO(String url, String content) {
        this.url = url;
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public String getContent() {
        return content;
    }
}
