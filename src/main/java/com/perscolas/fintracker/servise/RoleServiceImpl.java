package com.perscolas.fintracker.servise;

import com.perscolas.fintracker.exception.EntityNotFoundException;
import com.perscolas.fintracker.model.entity.Role;
import com.perscolas.fintracker.repository.RoleRepository;
import com.perscolas.fintracker.servise.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the RoleService interface.
 * - Provides functionality to retrieve the role of the current user, specifically the "ROLE_USER".
 * - Fetches the role from the repository and throws an exception if the role does not exist.
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getUserRole() {
        return roleRepository
                .findByName("ROLE_USER")
                .orElseThrow(() -> new EntityNotFoundException("Role does not exist"));
    }

}
