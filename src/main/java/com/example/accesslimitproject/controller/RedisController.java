package com.example.accesslimitproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @GetMapping("redis")
    public String testRedis(){
        redisTemplate.opsForValue().set("test","测试redis");
        return "1";
    }
}
