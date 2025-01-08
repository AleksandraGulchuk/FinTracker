package com.perscolas.fintracker.servise.interfaces;

import com.perscolas.fintracker.model.dto.user.UserSetupDto;

import java.util.UUID;

public interface UserService {

    void create(UserSetupDto userSetupDto);

    UUID getUserIdByUserName(String email);

}
