package com.perscolas.fintracker.servise;

import com.perscolas.fintracker.exception.EntityAlreadyExistsException;
import com.perscolas.fintracker.exception.EntityNotFoundException;
import com.perscolas.fintracker.model.mapper.UserMapper;
import com.perscolas.fintracker.model.dto.user.UserSetupDto;
import com.perscolas.fintracker.model.entity.UserAccess;
import com.perscolas.fintracker.model.entity.UserAccount;
import com.perscolas.fintracker.repository.UserAccountRepository;
import com.perscolas.fintracker.servise.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Implementation of the UserService interface for managing user accounts.
 * Handles user creation, password encryption, role assignment,
 * and retrieval of user ID by email. Ensures that duplicate users
 * are not created and sets default account settings.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserAccountRepository userRepository;
    private final RoleServiceImpl roleService;
    private final UserMapper userMapper;
    @Lazy
    private final BCryptPasswordEncoder encoder;

    @Override
    public void create(UserSetupDto userSetupDto) {
        checkIfUserAccountExists(userSetupDto);
        userSetupDto.setPassword(encoder.encode(userSetupDto.getPassword()));
        UserAccount user = userMapper.createUserFromDto(userSetupDto, createUserAccess());
        userRepository.save(user);
        log.info("User created successfully with email: {}", userSetupDto.getEmail());
    }

    @Override
    public UUID getUserIdByUserName(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + email + " not found"))
                .getId();
    }

    private void checkIfUserAccountExists(UserSetupDto userSetupDto) {
        Optional<UserAccount> userAccount = userRepository
                .findByEmail(userSetupDto.getEmail());
        if (userAccount.isPresent()) {
            log.warn("Failed to create user. User with email: {} already exists", userSetupDto.getEmail());
            throw new EntityAlreadyExistsException("User with email: " + userSetupDto.getEmail() + " already exists");
        }
    }

    private UserAccess createUserAccess() {
        return UserAccess.builder()
                .roles(Set.of(roleService.getUserRole()))
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .build();
    }

}
