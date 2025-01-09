package com.perscolas.fintracker.servise;

import com.perscolas.fintracker.exception.EntityAlreadyExistsException;
import com.perscolas.fintracker.exception.EntityNotFoundException;
import com.perscolas.fintracker.model.dto.user.UserSetupDto;
import com.perscolas.fintracker.model.entity.Role;
import com.perscolas.fintracker.model.entity.UserAccess;
import com.perscolas.fintracker.model.entity.UserAccount;
import com.perscolas.fintracker.model.mapper.UserMapper;
import com.perscolas.fintracker.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserAccountRepository userRepository;

    @Mock
    private RoleServiceImpl roleService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateShouldSaveUserWhenUserDoesNotExist() {
        UserSetupDto userSetupDto = new UserSetupDto();
        userSetupDto.setEmail("test@example.com");
        userSetupDto.setPassword("password");

        Role role = new Role(UUID.randomUUID(), "ROLE_USER");
        UserAccount userAccount = new UserAccount();

        when(roleService.getUserRole()).thenReturn(role);
        when(userRepository.findByEmail(userSetupDto.getEmail())).thenReturn(Optional.empty());
        when(encoder.encode(userSetupDto.getPassword())).thenReturn("encodedPassword");
        when(userMapper.createUserFromDto(any(UserSetupDto.class), any(UserAccess.class))).thenReturn(userAccount);

        userService.create(userSetupDto);
        verify(userRepository, times(1)).save(userAccount);
    }

    @Test
    void testCreateShouldThrowExceptionWhenUserAlreadyExists() {
        UserSetupDto userSetupDto = new UserSetupDto();
        userSetupDto.setEmail("test@example.com");

        UserAccount existingUser = new UserAccount();
        when(userRepository.findByEmail(userSetupDto.getEmail())).thenReturn(Optional.of(existingUser));

        assertThrows(EntityAlreadyExistsException.class, () -> userService.create(userSetupDto));
    }

    @Test
    void testGetUserIdByUserNameShouldReturnUserWhenUserExists() {
        String email = "test@example.com";
        UUID userId = UUID.randomUUID();
        UserAccount userAccount = new UserAccount();
        userAccount.setId(userId);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userAccount));

        UUID result = userService.getUserIdByUserName(email);
        assertEquals(userId, result);
    }

    @Test
    void testGetUserByUserNameShouldThrowExceptionWhenUserNotFound() {
        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserByUserName(email));
    }

}