package org.example.userauthservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.userauthservice.DTO.NewManagerCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewManagerCreatedEventProducer {
    private final KafkaTemplate<String, NewManagerCreatedEvent> kafkaTemplate;

    public void sendEvent(NewManagerCreatedEvent event) {
        log.info("Sending NewManagerCreatedEvent: {}", event);
        kafkaTemplate.send("new-manager-created-events", event);
    }
}
