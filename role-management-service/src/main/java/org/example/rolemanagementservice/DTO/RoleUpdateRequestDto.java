package org.example.rolemanagementservice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.rolemanagementservice.enums.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleUpdateRequestDto {
    @JsonProperty("oldRole")
    private Role oldRole;

    @JsonProperty("newRole")
    private Role newRole;
}
