package com.assignment3.scraper.webscraper.repository;

import com.assignment3.scraper.webscraper.model.ScrapedData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapedDataRepository extends JpaRepository<ScrapedData, Long> {
}