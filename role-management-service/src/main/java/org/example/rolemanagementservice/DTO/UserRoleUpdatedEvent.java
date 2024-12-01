package org.example.rolemanagementservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.rolemanagementservice.enums.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleUpdatedEvent {
    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("oldRole")
    private Role oldRole;

    @JsonProperty("newRole")
    private Role newRole;
}