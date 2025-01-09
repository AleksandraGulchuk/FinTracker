package com.perscolas.fintracker.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing the user login information.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {

    private String email;
    private String password;

}
