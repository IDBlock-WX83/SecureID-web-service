package com.example.webService.SocialServices.infrastructure.config;

import com.example.webService.SocialServices.domain.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationJobConfig implements Job {

    private final NotificationService notificationService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String topic = jobExecutionContext.getMergedJobDataMap().getString("topic");
        String title = jobExecutionContext.getMergedJobDataMap().getString("title");
        String body = jobExecutionContext.getMergedJobDataMap().getString("body");

        notificationService.sendToTopic(topic, title, body);
    }
}
