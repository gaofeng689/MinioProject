package com.example.accesslimitproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableRetry
public class AccessLimitProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccessLimitProjectApplication.class, args);
    }

}
