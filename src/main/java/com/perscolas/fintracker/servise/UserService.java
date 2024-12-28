package com.perscolas.fintracker.servise;

import com.perscolas.fintracker.exception.EntityAlreadyExistsException;
import com.perscolas.fintracker.exception.EntityNotFoundException;
import com.perscolas.fintracker.mapper.UserMapper;
import com.perscolas.fintracker.model.dto.user.UserSetupDto;
import com.perscolas.fintracker.model.entity.Role;
import com.perscolas.fintracker.model.entity.UserAccount;
import com.perscolas.fintracker.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAccountRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;

    public UUID create(UserSetupDto userSetupDto) {
        checkIfUserAccountExists(userSetupDto);
        Role role = roleService.getUserRole();
        UserAccount user = userMapper.createUserFromDto(userSetupDto, Set.of(role));
        return userRepository.save(user).getId();
    }

    public UUID getUserIdByUserName(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + email + " not found"))
                .getId();
    }

    private void checkIfUserAccountExists(UserSetupDto userSetupDto) {
        Optional<UserAccount> userAccount = userRepository
                .findByEmail(userSetupDto.getEmail());
        if (userAccount.isPresent()) {
            throw new EntityAlreadyExistsException("User with email: " + userSetupDto.getEmail() + " already exists");
        }
    }

}
