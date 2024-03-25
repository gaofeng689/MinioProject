package com.example.accesslimitproject.util;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduleUtil {

    @Scheduled(fixedRate = 300000)
    public void scheduleTask(){
        System.out.println("任务执行时间："+ LocalDateTime.now());
    }
}
