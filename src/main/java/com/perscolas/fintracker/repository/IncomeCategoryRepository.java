package com.perscolas.fintracker.repository;

import com.perscolas.fintracker.model.entity.IncomeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for accessing IncomeCategory entities in the database.
 * - Extends JpaRepository to provide CRUD operations for IncomeCategory with UUID as the ID type.
 */
@Repository
public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory, UUID> {

}
