package org.example.rolemanagementservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.rolemanagementservice.DTO.RoleUpdateRequestDto;
import org.example.rolemanagementservice.service.RoleManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/roles")
@RequiredArgsConstructor
public class RoleManagementController {

    private final RoleManagementService roleManagementService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update/{userId}")
    public ResponseEntity<Void> updateRole(@RequestHeader("Authorization") String token,
                                           @PathVariable Long userId,
                                           @RequestBody RoleUpdateRequestDto roleUpdateRequest) {
        roleManagementService.updateUserRole(token, userId, roleUpdateRequest);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
