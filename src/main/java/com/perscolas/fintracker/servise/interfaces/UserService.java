package com.perscolas.fintracker.servise.interfaces;

import com.perscolas.fintracker.model.dto.user.UserSetupDto;

import java.util.UUID;

/**
 * Interface for managing user-related operations.
 * - Allows creating a user from a user setup DTO.
 * - Provides a method to retrieve a user's ID by their email address.
 */
public interface UserService {

    void create(UserSetupDto userSetupDto);

    UUID getUserIdByUserName(String email);

}
