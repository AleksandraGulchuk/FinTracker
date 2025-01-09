package com.perscolas.fintracker.repository;

import com.perscolas.fintracker.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for accessing Role entities in the database.
 * - Extends JpaRepository to provide CRUD operations for Role with UUID as the ID type.
 * - Includes a custom query method to find a role by its name.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByName(String name);

}
