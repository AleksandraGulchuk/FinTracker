package com.perscolas.fintracker.repository;


import com.perscolas.fintracker.model.entity.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for accessing ExpenseCategory entities in the database.
 * - Extends JpaRepository to provide CRUD operations for ExpenseCategory with UUID as the ID type.
 */
@Repository
public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, UUID> {

}
