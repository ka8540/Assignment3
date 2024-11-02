package com.assignment3.scraper.webscraper;

import com.assignment3.scraper.webscraper.service.UrlFileProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@SpringBootApplication
public class WebscraperApplication {

	private static final String CSV_FILE_PATH = "/Users/kayahir/Desktop/Assignment3/webscraper/src/main/resources/scraped_data.csv";

	public static void main(String[] args) {
		SpringApplication.run(WebscraperApplication.class, args);
	}

	@Bean
	CommandLineRunner initCsvFile(UrlFileProcessor urlFileProcessor) {
		return args -> {
			// Initializing the CSV file with headers
			File csvFile = new File(CSV_FILE_PATH);
			if (!csvFile.exists()) {
				try (FileWriter fileWriter = new FileWriter(csvFile, true)) {
					fileWriter.append("URL,Content\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			// Processing URLs from the file
			String filePath = "/Users/kayahir/Desktop/Assignment3/webscraper/src/main/resources/urls.txt";
			urlFileProcessor.processUrlsFromFile(filePath);
		};
	}
}
