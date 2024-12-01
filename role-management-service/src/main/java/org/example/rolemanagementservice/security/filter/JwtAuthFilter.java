package org.example.rolemanagementservice.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.rolemanagementservice.utility.JwtUtility;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER_PREFIX = "Bearer ";
    private final JwtUtility jwtUtility;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith(AUTH_HEADER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(AUTH_HEADER_PREFIX.length());

        try {
            if (jwtUtility.isTokenExpired(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            String role = jwtUtility.extractRoleFromToken(token);

            if (role != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (!role.startsWith("ROLE_")) {
                    role = "ROLE_" + role;
                }
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        null, null, Collections.singleton(authority)
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            log.error("Auth fail", e);
        }
        filterChain.doFilter(request, response);
    }
}