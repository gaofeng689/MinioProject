package com.example.accesslimitproject.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Slf4j
@Component
@PropertySource("classpath:/task-config.ini")
public class ScheduleTask implements SchedulingConfigurer {

    @Value("${printTime.cron}")
    private String cron;

    private long seconds =120L;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                log.info("Current Time:{}", LocalDateTime.now());
            }
        },new Trigger(){

            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                //集成配置文件中的信息运行定时任务，间隔时间小于等于60秒
//                CronTrigger cronTrigger =new CronTrigger(cron);
//                return cronTrigger.nextExecutionTime(triggerContext);

                //可以设置间隔时间大于60秒
//                PeriodicTrigger periodicTrigger =new PeriodicTrigger(Long.valueOf(seconds), TimeUnit.SECONDS);
//                return periodicTrigger.nextExecutionTime(triggerContext);
                return null;
            }
        });
    }
}
