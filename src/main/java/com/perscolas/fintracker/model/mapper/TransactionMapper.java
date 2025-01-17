package com.perscolas.fintracker.model.mapper;

import com.perscolas.fintracker.model.dto.transaction.TransactionDto;
import com.perscolas.fintracker.model.entity.Expense;
import com.perscolas.fintracker.model.entity.ExpenseCategory;
import com.perscolas.fintracker.model.entity.Income;
import com.perscolas.fintracker.model.entity.IncomeCategory;
import com.perscolas.fintracker.util.Constants;
import org.springframework.stereotype.Component;

/**
 * Mapper component for converting between TransactionDto and entity objects (Expense and Income).
 * It provides methods for mapping both expense and income entities to DTOs, and vice versa.
 */
@Component
public class TransactionMapper {

    public TransactionDto expenseToDto(Expense expense) {
        return TransactionDto.builder()
                .transactionId(expense.getId())
                .type(Constants.EXPENSE)
                .category(expense.getExpenseCategory().getName())
                .categoryId(expense.getExpenseCategory().getId())
                .date(expense.getDate())
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .build();
    }

    public TransactionDto incomeToDto(Income income) {
        return TransactionDto.builder()
                .transactionId(income.getId())
                .type(Constants.INCOME)
                .category(income.getIncomeCategory().getName())
                .categoryId(income.getIncomeCategory().getId())
                .date(income.getDate())
                .amount(income.getAmount())
                .description(income.getDescription())
                .build();
    }

    public Income dtoToIncome(TransactionDto dto) {
        return Income.builder()
                .id(dto.getTransactionId())
                .incomeCategory(IncomeCategory.builder().id(dto.getCategoryId()).build())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .description(dto.getDescription())
                .build();
    }

    public Expense dtoToExpense(TransactionDto dto) {
        return Expense.builder()
                .id(dto.getTransactionId())
                .expenseCategory(ExpenseCategory.builder().id(dto.getCategoryId()).build())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .description(dto.getDescription())
                .build();
    }

}
