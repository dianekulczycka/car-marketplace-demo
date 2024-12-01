package org.example.userauthservice.configurations;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.userauthservice.security.filter.JwtAuthFilter;
import org.example.userauthservice.services.UserService;
import org.example.userauthservice.utilities.JwtUtility;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor

public class SecurityConfiguration {

    private final PasswordEncoder passwordEncoderConfig;
    private final UserService userService;
    private final JwtUtility jwtUtility;

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();

        daoAuthProvider.setPasswordEncoder(passwordEncoderConfig);
        daoAuthProvider.setUserDetailsService(userService);

        return daoAuthProvider;
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtUtility, userService);
    }

    @Bean
    @SneakyThrows
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .csrf(CsrfConfigurer::disable)
                .cors(CorsConfigurer::disable)
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/v1/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/api/users/id/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/api/users/email/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/v1/api/users/**").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/v1/api/users/**").hasAnyRole("MANAGER", "ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(new AuthExceptionHandler()))
                .build();
    }

    @Bean
    @SneakyThrows
    public AuthenticationManager authManager(AuthenticationConfiguration authConfig) {
        return authConfig.getAuthenticationManager();
    }
}