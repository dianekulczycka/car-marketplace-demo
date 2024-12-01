package org.example.rolemanagementservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.rolemanagementservice.DTO.UserRoleUpdatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoleEventsProducer {
    private final KafkaTemplate<Integer, UserRoleUpdatedEvent> kafkaTemplate;

    public void sendEvent(UserRoleUpdatedEvent userRoleUpdatedEvent) {
        log.info("Sending event: {}", userRoleUpdatedEvent);
        kafkaTemplate.send("user-role-updated-events", userRoleUpdatedEvent);
    }
}
