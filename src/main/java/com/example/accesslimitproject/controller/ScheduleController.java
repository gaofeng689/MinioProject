package com.example.accesslimitproject.controller;

import com.example.accesslimitproject.util.ScheduleTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ScheduleController {
    @Autowired
    private ScheduleTask scheduleTask;

    @GetMapping("updateCron")
    public String updateCron(String cron){
        log.info("new cron:{}",cron);
        scheduleTask.setCron(cron);
        return "success";
    }
}
