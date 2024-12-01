package org.example.postlistingservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.postlistingservice.DTO.PendingPostCreationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PendingPostCreationEventProducer {

    private final KafkaTemplate<String, PendingPostCreationEvent> kafkaTemplate;

    public void sendEvent(PendingPostCreationEvent event) {
        log.info("Sending event: {}", event);
        kafkaTemplate.send("pending-post-creation-events", event);
    }
}