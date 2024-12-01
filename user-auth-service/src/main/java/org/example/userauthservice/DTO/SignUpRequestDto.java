package org.example.userauthservice.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpRequestDto {
    @NotBlank(message = "Enter valid email")
    @Pattern(
            regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$",
            message = "Invalid email address pattern"
    )
    private String email;
    @NotBlank(message = "Enter valid password")
    @Pattern(
            regexp = "^(?=.*\\d)[A-Za-z\\d]{2,16}$",
            message = "Password must be 2-16 letters and contain at least one number, NO CYRILLIC LETTERS!"
    )
    private String password;
    @NotBlank(message = "Enter valid phone number")
    @Pattern(
            regexp = "^(?:\\+380|380|0)\\d{9}$",
            message = "Phone number must start with +380, 380, or 0 and contain 9 digits"
    )
    private String phoneNumber;
    @NotBlank(message = "Enter valid name")
    @Pattern(
            regexp = "^[A-Za-zА-Яа-яЁёіІїЇєЄ][A-Za-zА-Яа-яЁёіІїЇєЄ -]{1,30}[A-Za-zА-Яа-яЁёіІїЇєЄ]$",
            message = "Name must contain 2-32 letters, no numbers and symbols except for dash or whitespace"
    )
    private String fullName;
}
