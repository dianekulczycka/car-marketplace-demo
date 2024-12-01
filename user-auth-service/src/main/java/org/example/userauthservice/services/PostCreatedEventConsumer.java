package org.example.userauthservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.postlistingservice.DTO.PostCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostCreatedEventConsumer {
    private final UserService userService;

    @KafkaListener(topics = "user-post-created", groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(PostCreatedEvent event) {
        log.info("received user post created event: {}", event);
        userService.incrementUserPostCount(event.getUserEmail());
    }
}
