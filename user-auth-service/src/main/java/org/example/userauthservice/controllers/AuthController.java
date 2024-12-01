package org.example.userauthservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.userauthservice.DTO.*;
import org.example.userauthservice.services.UserService;
import org.example.userauthservice.utilities.JwtUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtility jwtUtility;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        SignUpResponseDto signUpResponseDto = userService.createUser(signUpRequestDto);
        return new ResponseEntity<>(signUpResponseDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> signIn(@RequestBody @Valid AuthRequestDto authRequestDto) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken
                (authRequestDto.getEmail(), authRequestDto.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);

        if (auth.isAuthenticated()) {
            UserDetails customer = userService.loadUserByUsername(authRequestDto.getEmail());
            String accessToken = jwtUtility.generateAccessToken(customer);
            String refreshToken = jwtUtility.generateRefreshToken(customer);
            return new ResponseEntity<>(AuthResponseDto
                    .builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build(),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDto> getNewTokenPair(@RequestBody @Valid RefreshTokenRequestDto refreshTokenRequestDto) {
        String refreshToken = refreshTokenRequestDto.getRefreshToken();
        try {
            if (jwtUtility.isTokenExpired(refreshToken)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            String username = jwtUtility.extractUsername(refreshToken);
            UserDetails user = userService.loadUserByUsername(username);

            String newAccessToken = jwtUtility.generateAccessToken(user);
            String newRefreshToken = jwtUtility.generateRefreshToken(user);

            return new ResponseEntity<>(AuthResponseDto.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .build(), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
