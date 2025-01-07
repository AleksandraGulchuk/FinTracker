package com.perscolas.fintracker.mapper;

import com.perscolas.fintracker.model.dto.user.UserSetupDto;
import com.perscolas.fintracker.model.entity.UserAccess;
import com.perscolas.fintracker.model.entity.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserAccount createUserFromDto(UserSetupDto userSetupDto, UserAccess userAccess) {
        return UserAccount.builder()
                .name(userSetupDto.getName())
                .email(userSetupDto.getEmail())
                .password(userSetupDto.getPassword())
                .userAccess(userAccess)
                .build();
    }

}
