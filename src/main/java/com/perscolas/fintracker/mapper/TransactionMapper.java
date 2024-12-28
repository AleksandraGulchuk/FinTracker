package com.perscolas.fintracker.mapper;

import com.perscolas.fintracker.model.dto.transaction.TransactionDto;
import com.perscolas.fintracker.model.dto.transaction.TransactionSaveDto;
import com.perscolas.fintracker.model.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionMapper {

    public TransactionDto expenseToDto(Expense expense) {
        return TransactionDto.builder()
                .id(expense.getId())
                .type("expense")
                .category(expense.getExpenseCategory().getName())
                .date(expense.getDate())
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .build();
    }

    public List<TransactionDto> expensesToDto(List<Expense> expenses) {
        return expenses.stream()
                .map(this::expenseToDto)
                .toList();
    }

    public TransactionDto incomeToDto(Income income) {
        return TransactionDto.builder()
                .id(income.getId())
                .type("income")
                .category(income.getIncomeCategory().getName())
                .date(income.getDate())
                .amount(income.getAmount())
                .description(income.getDescription())
                .build();
    }

    public List<TransactionDto> incomesToDto(List<Income> incomes) {
        return incomes.stream()
                .map(this::incomeToDto)
                .toList();
    }

    public Income dtoToIncome(TransactionSaveDto dto) {
        return Income.builder()
                .userAccount(UserAccount.builder().id(dto.getUserId()).build())
                .incomeCategory(IncomeCategory.builder().id(dto.getCategoryId()).build())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .description(dto.getDescription())
                .build();
    }

    public Expense dtoToExpense(TransactionSaveDto dto) {
        return Expense.builder()
                .userAccount(UserAccount.builder().id(dto.getUserId()).build())
                .expenseCategory(ExpenseCategory.builder().id(dto.getCategoryId()).build())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .description(dto.getDescription())
                .build();
    }

}
