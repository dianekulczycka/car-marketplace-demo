package org.example.postlistingservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.postlistingservice.DTO.PostCheckRequestEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostCheckRequestEventProducer {

    private final KafkaTemplate<String, PostCheckRequestEvent> kafkaTemplate;

    public void sendEvent(PostCheckRequestEvent requestEvent) {
        log.info("Sending event: {}", requestEvent);
        kafkaTemplate.send("user-post-check-request-events", requestEvent);
    }
}