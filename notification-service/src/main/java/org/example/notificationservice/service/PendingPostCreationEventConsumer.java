package org.example.notificationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.postlistingservice.DTO.PendingPostCreationEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PendingPostCreationEventConsumer {
    private final NotificationService notificationService;

    @KafkaListener(topics = "pending-post-creation-events", groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(PendingPostCreationEvent event) {
        log.info("Received pending-post-creation-event: {}", event);
        notificationService.notifyManagers(event.getPostId());
    }

}
