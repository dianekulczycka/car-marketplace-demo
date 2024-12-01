package org.example.userauthservice.mappers;

import org.example.userauthservice.DTO.UserDto;
import org.example.userauthservice.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto mapToDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setEmail(user.getEmail());
        userDto.setFullName(user.getFullName());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setPostCount(user.getPostCount());
        return userDto;
    }

    public User mapToEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPostCount(0);
        return user;
    }
}
