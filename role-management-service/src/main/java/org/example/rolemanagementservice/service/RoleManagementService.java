package org.example.rolemanagementservice.service;

import lombok.RequiredArgsConstructor;
import org.example.rolemanagementservice.DTO.RoleUpdateRequestDto;
import org.example.rolemanagementservice.DTO.UserRoleUpdatedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleManagementService {
    private final RoleEventsProducer roleEventsProducer;

    @Transactional
    public void updateUserRole(String token, Long userId, RoleUpdateRequestDto roleUpdateRequest) {
        UserRoleUpdatedEvent userRoleUpdatedEvent = new UserRoleUpdatedEvent(userId,
                roleUpdateRequest.getOldRole(),
                roleUpdateRequest.getNewRole());

        roleEventsProducer.sendEvent(userRoleUpdatedEvent);
    }

}
