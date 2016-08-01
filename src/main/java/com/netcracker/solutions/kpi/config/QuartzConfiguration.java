package com.netcracker.solutions.kpi.config;

import com.netcracker.solutions.kpi.service.util.SendMessageJob;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan("com.netcracker.solutions.kpi.service.*")
public class QuartzConfiguration {

    @Bean
    public JobDetailFactoryBean jobDetailResendMessage() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(SendMessageJob.class);
        Map<String, Object> map = new HashMap<>();
        map.put("name", "ResendJob");
        factory.setJobDataAsMap(map);
        factory.setGroup("resendGroup");
        factory.setName("resendMessageJob");
        return factory;
    }

    @Bean
    public CronTriggerFactoryBean cronTriggerResendMessage() {
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(jobDetailResendMessage().getObject());
        stFactory.setStartDelay(3000);
        stFactory.setName("mytrigger");
        stFactory.setGroup("myGroup");
        stFactory.setCronExpression("0 0/1 * 1/1 * ? *");
        return stFactory;
    }

    
    @Bean
    public SchedulerFactoryBean schedulerResendMessage() {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(cronTriggerResendMessage().getObject());
        return scheduler;
    }

}
