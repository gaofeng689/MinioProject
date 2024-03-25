package com.example.accesslimitproject.controller;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("caffine")
public class CaffineController {

    @Autowired
    private Cache<String,Object> caffineCache;

    @GetMapping("getIfPresent")
    public Object getIfPresent(){
        caffineCache.put("caffinekey","caffinevalue");
        return caffineCache.getIfPresent("caffinekey");
    }

    @GetMapping("get")
    public Object get(){
        caffineCache.invalidate("caffinekey");
        return caffineCache.get("caffinekey",key->getValue(key));
    }

    private Object getValue(String key) {
        return key+":value";
    }

    @GetMapping("cachePut")
    @CachePut(value = "user", key = "1")
    public String CachePut() {
        return "2";
    }

    @GetMapping("cacheEvict")
    @CacheEvict(value = "user", key = "1")
    public String CacheEvict() {
        return "3";
    }

    @GetMapping("cacheable")
    @Cacheable(value = "user", key = "1")
    public String cacheable() {
        return "1";
    }
}
