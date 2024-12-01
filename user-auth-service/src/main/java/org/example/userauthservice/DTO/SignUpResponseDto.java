package org.example.userauthservice.DTO;

import lombok.Builder;
import lombok.Data;
import org.example.userauthservice.enums.AccountType;
import org.example.userauthservice.enums.Role;

import java.time.LocalDateTime;

@Data
@Builder

public class SignUpResponseDto {
    private Long userId;
    private String email;
    private Role role;
    private AccountType accountType;
    private LocalDateTime registrationDate;
}
