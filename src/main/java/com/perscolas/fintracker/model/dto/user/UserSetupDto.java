package com.perscolas.fintracker.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSetupDto {

    @NotBlank(message = "Name must not be empty")
    @Pattern(regexp = "^[a-zA-Z]+",
            message = "Name must contain only letters from a-z")
    private String name;
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character")
    private String password;

}
