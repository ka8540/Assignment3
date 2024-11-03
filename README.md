# Assignment 3: Thread Pooling Implementation for Web Content Scraping

## Project Overview

Our project aims to implement a thread pool to improve the performance of web content scraping and parsing tasks. Specifically, we developed an application to scrape product information from an e-commerce website, such as Amazon, and store the extracted data in a database for display in a tabular format.

### Project Domain

**Web Content Scraping and Parsing**  
This project involves scraping data from web pages, parsing HTML content to extract specific information (like product details), and storing it in a database. This data can be displayed in tables for further analysis.

## Prerequisites

- **Java 21**
- **Maven** (Compatible with Java 21)

## How to Run the Application

1. Clone the repository and navigate to the project root directory.
2. Run the following command to start the application:
   ```bash
   ./mvnw spring-boot:run
   ```
3. To pass a POST request for scraping, use:

   ```curl -i -X POST "http://localhost:8080/api/scrape/file"

   ```

## Project Idea

The goal of this project is to create a thread pool to handle performance-critical tasks like scraping and parsing web content. Scraping large volumes of data or multiple pages is time-consuming and resource-intensive. By using a thread pool, we can efficiently manage multiple tasks in parallel, reducing execution time.

## Implementation Details

### Task Selection

Scraping and parsing HTML content to extract product information from e-commerce websites. This data is then stored in a database for display in tables.

### Thread Pool

A thread pool of 10 threads is created to handle scraping tasks. Each thread processes a different URL or data chunk, improving performance by parallelizing tasks. When a thread completes its task, it is reused for subsequent tasks from the queue.

### Performance Measurement

To evaluate the efficiency of thread pooling, we compare the execution times of single-threaded and thread-pooled operations.

- **Single-Threaded Execution:** The application scrapes each URL sequentially, one at a time. This mode simulates a simple, non-parallel scraping process. It gives a baseline time measurement for how long the task would take without concurrency.

  - Example log entry:
    ```
    Starting single-threaded scraping for URL: https://www.example.com
    Completed single-threaded scraping for URL: https://www.example.com
    Single-threaded scraping took: 68736 ms
    ```

- **Thread-Pooled Execution:** A thread pool of 10 threads is used to execute multiple scraping tasks in parallel. Each thread picks up a different URL or data chunk, resulting in faster processing. The total time taken is significantly reduced as each thread handles part of the workload concurrently.
  - Example log entry:
    ```
    Starting scraping for URL: https://www.example.com on thread: pool-1-thread-2
    Completed scraping for URL: https://www.example.com on thread: pool-1-thread-2
    Thread-pooled scraping took: 2 ms
    ```

This comparison demonstrates the performance improvement achieved through multithreading, with a notable reduction in execution time for thread-pooled processing.

#### Example Real-World Usage

- Parallel parsing of HTML elements during web scraping
- Scraping product information from e-commerce sites, useful for data analysis, price comparison, or market research.

### Thread Pool Size

We have configured the pool to contain 10 threads. Each thread is dedicated to a specific element or parsing task, enabling efficient data handling.

## Input and Output Files

### Input File: Contains URLs for scraping, located at:

    ```Assignment3/webscraper/src/main/resources/urls.txt```

### Output File: Contains scraped data, saved as a .csv file at:

    ``` Assignment3/webscraper/scraped_data.csv```

## Output
