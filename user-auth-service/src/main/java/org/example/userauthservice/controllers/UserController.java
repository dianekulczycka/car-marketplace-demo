package org.example.userauthservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.userauthservice.DTO.UserDto;
import org.example.userauthservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(this.userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<UserDto>> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(this.userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Optional<UserDto>> getUserById(@PathVariable String email) {
        return new ResponseEntity<>(this.userService.getUserByEmail(email), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MANAGER')")
    @PutMapping("/")
    public ResponseEntity<Void> updateUser(@RequestBody UserDto userDto) {
        userService.updateUser(userDto.getUserId(), userDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        this.userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
