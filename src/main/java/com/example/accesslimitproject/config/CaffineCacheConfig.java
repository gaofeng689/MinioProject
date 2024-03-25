package com.example.accesslimitproject.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CaffineCacheConfig {

    @Bean
    public Cache<String,Object> caffineCache(){
        return Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .initialCapacity(100)
                .maximumSize(1000)
                .build();
    }

    @Bean
    public CacheManager cacheManager(){
        return new CaffeineCacheManager();
    }
}
