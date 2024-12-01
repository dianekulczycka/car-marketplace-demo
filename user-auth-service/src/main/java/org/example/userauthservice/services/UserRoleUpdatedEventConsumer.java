package org.example.userauthservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.rolemanagementservice.DTO.UserRoleUpdatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRoleUpdatedEventConsumer implements MessageListener<Long, UserRoleUpdatedEvent> {
    private final UserService userService;

    @KafkaListener(topics = "user-role-updated-events", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void onMessage(ConsumerRecord<Long, UserRoleUpdatedEvent> data) {
        UserRoleUpdatedEvent event = data.value();
        if (event.getUserId() == null) {
            throw new IllegalArgumentException("Received userid is null");
        }
        log.info("Received user role updated event: {}", event);
        userService.updateUserRole(event.getUserId(), event.getNewRole());
    }
}