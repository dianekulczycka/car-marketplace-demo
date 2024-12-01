package org.example.userauthservice.DTO;

import lombok.Data;

@Data
public class UserDto {
    private Long userId;
    private String email;
    private String fullName;
    private String phoneNumber;
    private Integer postCount;
}
