package com.example.accesslimitproject.controller;

import com.example.accesslimitproject.service.RetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetryController {

    @Autowired
    private RetryService retryService;

    @GetMapping("retry")
    public String retry() {
        try {
            retryService.test();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "retry";
    }
}
