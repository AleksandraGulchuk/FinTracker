package com.perscolas.fintracker.servise.interfaces;

import com.perscolas.fintracker.model.dto.transaction.SummaryDto;
import com.perscolas.fintracker.model.dto.transaction.TransactionDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing expenses.
 * - Provides methods to retrieve an expense summary, create, delete, and update expenses.
 * - Allows fetching all expenses for a user after a specific date.
 */

public interface ExpenseService {

    SummaryDto getSummary(String userName, String timeDuration);

    void createExpense(String userName, TransactionDto dto);

    void deleteExpense(UUID incomeId);

    void updateExpense(String userName, TransactionDto transaction);

    List<TransactionDto> findAllExpensesByUserIdAndDateAfter(UUID userId, LocalDate date);

}
