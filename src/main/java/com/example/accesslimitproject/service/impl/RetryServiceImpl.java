package com.example.accesslimitproject.service.impl;

import com.example.accesslimitproject.service.RetryService;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RetryServiceImpl implements RetryService {
    @Override
    @Retryable(recover = "recoverTest1",value = Exception.class, maxAttempts = 3,backoff = @Backoff(delay = 1000))
    public void test() throws Exception {
        System.out.println("test");
        System.out.println(new Date());
        throw new Exception();
    }

    @Recover
    public void recoverTest(Exception e) {
        System.out.println("回调方法-" + e.getMessage());
    }

    @Recover
    public void recoverTest1(Exception e) {
        System.out.println("回调方法1-" + e.getMessage());
    }
}
