package com.perscolas.fintracker.servise;

import com.perscolas.fintracker.mapper.CategoryMapper;
import com.perscolas.fintracker.mapper.TransactionMapper;
import com.perscolas.fintracker.model.dto.category.CategoryDto;
import com.perscolas.fintracker.model.dto.transaction.SummaryDto;
import com.perscolas.fintracker.model.dto.transaction.TransactionDto;
import com.perscolas.fintracker.model.entity.Expense;
import com.perscolas.fintracker.repository.ExpenseCategoryRepository;
import com.perscolas.fintracker.repository.ExpenseRepository;
import com.perscolas.fintracker.util.SummaryCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final TransactionMapper transactionMapper;
    private final CategoryMapper categoryMapper;
    private final UserService userService;


    public SummaryDto getSummary(String userName, String timeDuration) {
        UUID userId = userService.getUserIdByUserName(userName);
        List<TransactionDto> transactions = getTransactions(userId);
        Map<String, BigDecimal> summary = SummaryCalculator.calculateTransactionHistory(transactions, timeDuration);
        return SummaryDto.builder()
                .transactions(transactions)
                .categories(getCategories())
                .transactionChart(summary)
                .build();
    }

    public void createExpense(String userName, TransactionDto dto) {
        UUID userId = userService.getUserIdByUserName(userName);
        dto.setUserId(userId);
        Expense expense = transactionMapper.dtoToExpense(dto);
        expenseRepository.save(expense);
    }

    public void deleteExpense(UUID incomeId) {
        expenseRepository.deleteById(incomeId);
    }

    public void updateExpense(String userName, TransactionDto transaction) {
        UUID userId = userService.getUserIdByUserName(userName);
        transaction.setUserId(userId);
        Expense expense = transactionMapper.dtoToExpense(transaction);
        expenseRepository.save(expense);
    }

    public List<Expense> findAllExpensesByUserIdAndDateAfter(UUID userId, LocalDate date) {
        return expenseRepository.findAllByUserAccountIdAndDateAfterOrderByDateDesc(userId, date);
    }

    private List<CategoryDto> getCategories() {
        return expenseCategoryRepository
                .findAll()
                .stream()
                .map(categoryMapper::expenseCategoryToDto)
                .toList();
    }

    //TODO: MAKE DECISION ABOUT ALL TRANSACTIONS INSTEAD OF HALF YEAR
    private List<TransactionDto> getTransactions(UUID id) {
        return expenseRepository
                .findAllByUserAccountIdOrderByDateDesc(id)
                .stream()
                .map(transactionMapper::expenseToDto)
                .toList();
    }

}
