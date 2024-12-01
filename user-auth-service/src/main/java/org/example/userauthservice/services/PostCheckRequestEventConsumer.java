package org.example.userauthservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.postlistingservice.DTO.PostCheckRequestEvent;
import org.example.userauthservice.DTO.PostCheckResponseEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostCheckRequestEventConsumer {

    private final UserService userService;
    private final KafkaTemplate<String, PostCheckResponseEvent> kafkaTemplate;

    @KafkaListener(topics = "user-post-check-request-events", groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(ConsumerRecord<String, PostCheckRequestEvent> record) {
        log.info("Received user post check request event: {}", record.value());

        PostCheckRequestEvent requestEvent = record.value();
        boolean canPost = userService.canUserPost(requestEvent.getUserEmail());

        PostCheckResponseEvent responseEvent = new PostCheckResponseEvent();
        responseEvent.setUserEmail(requestEvent.getUserEmail());
        responseEvent.setCanMakePost(canPost);

        log.info("Sending user post check response event: {}", responseEvent);
        kafkaTemplate.send("user-post-check-response-events", responseEvent);
    }
}
