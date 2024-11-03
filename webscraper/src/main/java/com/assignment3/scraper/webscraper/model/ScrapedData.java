package com.assignment3.scraper.webscraper.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class ScrapedData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String title;
    private BigDecimal price;
    @Lob // Large object annotation to handle large content
    private String content;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

//package com.assignment3.scraper.webscraper.model;
//
//        import jakarta.persistence.Entity;
//        import jakarta.persistence.GeneratedValue;
//        import jakarta.persistence.GenerationType;
//        import jakarta.persistence.Id;
//        import jakarta.persistence.Lob;
//
//@Entity
//public class ScrapedData {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id; // Primary key for the entity
//
//    private String url; // The URL that was scraped
//
//    @Lob // Allows storage of large text data
//    private String content; // The scraped content
//
//    // Constructors
//    public ScrapedData() {
//    }
//
//    public ScrapedData(String url, String content) {
//        this.url = url;
//        this.content = content;
//    }
//
//    // Getters and Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//}