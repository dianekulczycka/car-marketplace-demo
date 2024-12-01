package org.example.postlistingservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.postlistingservice.DTO.PostCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostCreatedEventProducer {
    private final KafkaTemplate<String, PostCreatedEvent> kafkaTemplate;

    public void sendEvent(PostCreatedEvent event) {
        log.info("Sending event: {}", event);
        kafkaTemplate.send("user-post-created", event);
    }
}