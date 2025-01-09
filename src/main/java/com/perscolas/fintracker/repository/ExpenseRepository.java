package com.perscolas.fintracker.repository;

import com.perscolas.fintracker.model.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface for accessing Expense entities in the database.
 * - Extends JpaRepository to provide CRUD operations for Expense with UUID as the ID type.
 * - Includes a custom query method to find all expenses for a user after a specific date, ordered by date in descending order.
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    List<Expense> findAllByUserAccountIdAndDateAfterOrderByDateDesc(UUID userAccountId, LocalDate date);

}
