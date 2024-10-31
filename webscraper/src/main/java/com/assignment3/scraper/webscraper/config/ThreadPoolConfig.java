package com.assignment3.scraper.webscraper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ThreadPoolConfig {

    @Bean(name = "scraperThreadPool")
    public Executor scraperThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20); // Minimum number of threads to keep in the pool
        executor.setMaxPoolSize(50); // Maximum number of threads in the pool
        executor.setQueueCapacity(100); // Number of tasks that can be queued
        executor.setThreadNamePrefix("ScraperThread-"); // Naming pattern for threads
        executor.initialize();
        return executor;
    }
}
