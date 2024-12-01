package org.example.userauthservice.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.userauthservice.enums.AccountType;
import org.example.userauthservice.enums.Role;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@DynamicUpdate

@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique=true, nullable=false)
    private String email;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false, name="full_name")
    private String fullName;

    @Column(nullable=false, name="phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name="account_type", nullable = false)
    private AccountType accountType;

    @Column(name="registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name="is_shown", nullable = false)
    private boolean isShown;

    @Column(name="post_count", nullable = false)
    private int postCount;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }
}
