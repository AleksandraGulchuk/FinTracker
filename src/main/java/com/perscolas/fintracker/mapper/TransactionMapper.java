package com.perscolas.fintracker.mapper;

import com.perscolas.fintracker.model.dto.transaction.TransactionDto;
import com.perscolas.fintracker.model.entity.*;
import com.perscolas.fintracker.util.Constants;
import org.springframework.stereotype.Component;

import java.util.List;

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
                .userAccount(UserAccount.builder().id(dto.getUserId()).build())
                .incomeCategory(IncomeCategory.builder().id(dto.getCategoryId()).build())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .description(dto.getDescription())
                .build();
    }

    public Expense dtoToExpense(TransactionDto dto) {
        return Expense.builder()
                .id(dto.getTransactionId())
                .userAccount(UserAccount.builder().id(dto.getUserId()).build())
                .expenseCategory(ExpenseCategory.builder().id(dto.getCategoryId()).build())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .description(dto.getDescription())
                .build();
    }

}
