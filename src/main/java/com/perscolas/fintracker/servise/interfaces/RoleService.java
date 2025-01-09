package com.perscolas.fintracker.servise.interfaces;

import com.perscolas.fintracker.model.entity.Role;

/**
 * Service interface for managing user roles.
 * - Provides a method to get the role of the current user.
 */
public interface RoleService {

    Role getUserRole();

}
