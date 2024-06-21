package com.deepak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.deepak")
public class APIBestPracticeApp {
    public static void main(String[] args) {
        SpringApplication.run(APIBestPracticeApp.class, args);
    }
}