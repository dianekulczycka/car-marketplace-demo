package org.example.notificationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.userauthservice.DTO.NewManagerCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ManagerCreatedEventConsumer {
    private final NotificationService notificationService;

    @KafkaListener(topics = "new-manager-created-events", groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(NewManagerCreatedEvent event) {
        log.info("Received new-manager-created-event: {}", event);
        String userEmail = event.getEmail();
        notificationService.addManagerEmail(userEmail);
    }
}
