package com.example.webService.SocialServices.infrastructure.messaging;

import com.example.webService.SocialServices.domain.services.NotificationService;
import com.example.webService.SocialServices.infrastructure.config.NotificationJobConfig;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirebaseMessagingService implements NotificationService {

    private final Scheduler scheduler;

    @Override
    public void sendToTopic(String topic, String title, String body) {
        try {
            Message message = Message.builder()
                    .setTopic(topic)
                    .putData("title", title)
                    .putData("body", body)
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            log.info("✅ Notification sent to topic {}: {}", topic, response);
        }
        catch (Exception e) {
            log.error("❌ Error sending notification to topic {}", topic, e);
        }
    }

    @Override
    public void scheduleNotification(String topic, String title, String body, LocalDateTime when, String jobId) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(NotificationJobConfig.class)
                    .withIdentity("job_" + jobId, "notifications")
                    .usingJobData("topic", topic)
                    .usingJobData("title", title)
                    .usingJobData("body", body)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger_" + jobId, "notifications")
                    .startAt(Date.from(when.atZone(ZoneId.systemDefault()).toInstant()))
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
        }
        catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelNotification(String jobId) {
        try {
            scheduler.deleteJob(new JobKey("job_" + jobId, "notifications"));
        }
        catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
