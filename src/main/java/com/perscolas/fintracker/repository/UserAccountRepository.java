package com.perscolas.fintracker.repository;

import com.perscolas.fintracker.model.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for accessing UserAccount entities in the database.
 * - Extends JpaRepository to provide CRUD operations for UserAccount with UUID as the ID type.
 * - Includes a custom query method to find a user account by its email.
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {

    Optional<UserAccount> findByEmail(String email);

}
