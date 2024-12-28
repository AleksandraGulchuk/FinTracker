package com.perscolas.fintracker.repository;

import com.perscolas.fintracker.model.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    List<Expense> findAllByUserAccountIdOrderByDateDesc(UUID userAccountId);

    List<Expense> findAllByUserAccountIdAndDateAfterOrderByDateDesc(UUID userAccountId, LocalDate date);

}
