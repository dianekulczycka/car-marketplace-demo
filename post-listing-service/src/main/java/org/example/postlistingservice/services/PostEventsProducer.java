package org.example.postlistingservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.postlistingservice.DTO.PostStatsUpdatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostEventsProducer {
    private final KafkaTemplate<Integer, PostStatsUpdatedEvent> kafkaTemplate;

    public void sendEvent(PostStatsUpdatedEvent postStatsUpdatedEvent) {
        log.info("Sending event: {}", postStatsUpdatedEvent);
        kafkaTemplate.send("post-stats-updated-events", postStatsUpdatedEvent);
    }
}
