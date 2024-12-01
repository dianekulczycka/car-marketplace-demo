package org.example.postlistingservice.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.userauthservice.DTO.PostCheckResponseEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostCheckResponseEventConsumer {

    private final PostService postService;

    @KafkaListener(topics = "user-post-check-response-events", groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(PostCheckResponseEvent event) {
        log.info("Received user-post-check-response-event: {}", event);
        postService.publishAPost(event.getUserEmail(), event.isCanMakePost());
    }
}