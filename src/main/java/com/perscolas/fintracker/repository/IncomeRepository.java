package com.perscolas.fintracker.repository;


import com.perscolas.fintracker.model.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface for accessing Income entities in the database.
 * - Extends JpaRepository to provide CRUD operations for Income with UUID as the ID type.
 * - Includes a custom query method to find all incomes for a user after a specific date, ordered by date in descending order.
 */
@Repository
public interface IncomeRepository extends JpaRepository<Income, UUID> {

    List<Income> findAllByUserAccountIdAndDateAfterOrderByDateDesc(UUID userAccountId, LocalDate date);

}
