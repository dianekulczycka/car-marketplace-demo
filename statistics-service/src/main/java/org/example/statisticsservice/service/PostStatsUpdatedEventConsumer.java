package org.example.statisticsservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.postlistingservice.DTO.PostStatsUpdatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor

public class PostStatsUpdatedEventConsumer implements MessageListener<Long, PostStatsUpdatedEvent> {
    private final PostStatsService postStatsService;

    @KafkaListener(topics = "post-stats-updated-events", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void onMessage(ConsumerRecord<Long, PostStatsUpdatedEvent> data) {
        PostStatsUpdatedEvent event = data.value();

        if (event.postId() == null) {
            throw new IllegalArgumentException("Received postid is null");
        }

        log.info("Received post-stats-updated-event: {}", event);
        postStatsService.updateViews(event.postId());
    }
}


