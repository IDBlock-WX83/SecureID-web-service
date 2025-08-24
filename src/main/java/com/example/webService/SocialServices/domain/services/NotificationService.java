package com.example.webService.SocialServices.domain.services;

import java.time.LocalDateTime;

public interface NotificationService {
    void sendToTopic(String topic, String title, String body);
    void scheduleNotification(String topic, String title, String body, LocalDateTime when, String jobId);
    void cancelNotification(String jobId);
}
