package com.perscolas.fintracker.mapper;

import com.perscolas.fintracker.model.dto.user.UserSetupDto;
import com.perscolas.fintracker.model.entity.Role;
import com.perscolas.fintracker.model.entity.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserMapper {

    @Lazy
    private final BCryptPasswordEncoder encoder;

    public UserAccount createUserFromDto(UserSetupDto userSetupDto, Set<Role> roles) {
        return UserAccount.builder()
                .name(userSetupDto.getName())
                .email(userSetupDto.getEmail())
                .password(encoder.encode(userSetupDto.getPassword()))
                .roles(roles)
                .build();
    }
}
