package org.example.userauthservice.services;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.userauthservice.DTO.NewManagerCreatedEvent;
import org.example.userauthservice.DTO.SignUpRequestDto;
import org.example.userauthservice.DTO.SignUpResponseDto;
import org.example.userauthservice.DTO.UserDto;
import org.example.userauthservice.entities.User;
import org.example.userauthservice.enums.AccountType;
import org.example.userauthservice.enums.Role;
import org.example.userauthservice.mappers.UserMapper;
import org.example.userauthservice.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final NewManagerCreatedEventProducer newManagerCreatedEventProducer;

    public List<UserDto> getAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .filter(User::isShown)
                .map(this.userMapper::mapToDto)
                .toList();
    }

    public Optional<UserDto> getUserById(Long id) {
        return this.userRepository
                .findById(id)
                .filter(User::isShown)
                .map(this.userMapper::mapToDto);
    }

    public Optional<UserDto> getUserByEmail(String email) {
        return this.userRepository
                .findByEmail(email)
                .filter(User::isShown)
                .map(this.userMapper::mapToDto);
    }

    public SignUpResponseDto createUser(@Valid SignUpRequestDto signUpRequestDto) {
        String password = this.passwordEncoder.encode(signUpRequestDto.getPassword());
        User user = new User();
        user.setEmail(signUpRequestDto.getEmail());
        user.setPassword(password);
        user.setPhoneNumber(signUpRequestDto.getPhoneNumber());
        user.setFullName(signUpRequestDto.getFullName());
        user.setRole(Role.ROLE_SELLER);
        user.setAccountType(AccountType.DEFAULT);
        user.setRegistrationDate(LocalDateTime.now());
        user.setShown(true);
        this.userRepository.save(user);

        return SignUpResponseDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .role(user.getRole())
                .accountType(user.getAccountType())
                .registrationDate(user.getRegistrationDate())
                .build();
    }

    public void updateUser(Long id, UserDto userDto) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with provided id was not found!"));
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getFullName() != null) {
            user.setFullName(userDto.getFullName());
        }
        if (userDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }
        this.userRepository.save(user);
    }

    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with provided email was not found!"));
    }


    @Transactional
    public void updateUserRole(Long userId, Role newRole) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user with given id not found"));

        log.info("Updating user with id {} to role {}", userId, newRole);
        user.setRole(newRole);
        user.setShown(newRole != Role.ROLE_MANAGER);
        if (newRole == Role.ROLE_MANAGER) {
            user.setAccountType(AccountType.PREMIUM);
        } else if (newRole == Role.ROLE_SELLER) {
            user.setAccountType(AccountType.DEFAULT);
        }

        this.userRepository.save(user);

        if (newRole == Role.ROLE_MANAGER) {
            NewManagerCreatedEvent event = new NewManagerCreatedEvent(user.getEmail());
            this.newManagerCreatedEventProducer.sendEvent(event);
        }

    }

    public void incrementUserPostCount(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPostCount(user.getPostCount() + 1);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("user does not exist");
        }
    }

    public boolean canUserPost(String email) {
        User user = this.userRepository.findByEmail(email).get();

        boolean isPremium = user.getAccountType() == AccountType.PREMIUM;
        boolean hasPosts = user.getPostCount() != 0;

        return isPremium || !hasPosts;
    }
}
