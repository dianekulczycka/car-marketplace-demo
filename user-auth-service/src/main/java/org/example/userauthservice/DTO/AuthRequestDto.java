package org.example.userauthservice.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequestDto {
    @NotBlank(message = "Username must not be empty!")
    private String email;

    @NotBlank(message = "Password must not be empty!")
    private String password;
}
