package com.assignment3.scraper.webscraper;

import com.assignment3.scraper.webscraper.service.UrlFileProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebscraperApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebscraperApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UrlFileProcessor urlFileProcessor) {
		return args -> {
			String filePath = "/Users/kayahir/Desktop/Assignment3/webscraper/src/main/resources/urls.txt";
			urlFileProcessor.processUrlsFromFile(filePath);
		};
	}
}
