package com.assignment3.scraper.webscraper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPoolConfig {

    /**

     Defines a fixed thread pool of 10 threads as a bean.*
     @return ExecutorService with a fixed thread pool of 10 threads*/@Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(10);}
}