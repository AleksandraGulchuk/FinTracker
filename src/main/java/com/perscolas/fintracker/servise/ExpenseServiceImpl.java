package com.perscolas.fintracker.servise;

import com.perscolas.fintracker.exception.EntityNotFoundException;
import com.perscolas.fintracker.model.mapper.CategoryMapper;
import com.perscolas.fintracker.model.mapper.TransactionMapper;
import com.perscolas.fintracker.model.dto.category.CategoryDto;
import com.perscolas.fintracker.model.dto.transaction.SummaryDto;
import com.perscolas.fintracker.model.dto.transaction.TransactionDto;
import com.perscolas.fintracker.model.entity.Expense;
import com.perscolas.fintracker.repository.ExpenseCategoryRepository;
import com.perscolas.fintracker.repository.ExpenseRepository;
import com.perscolas.fintracker.servise.interfaces.ExpenseService;
import com.perscolas.fintracker.util.DateCalculator;
import com.perscolas.fintracker.util.TransactionSummaryCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Implementation of the ExpenseService interface.
 * - Provides methods to manage expenses, including getting summaries, creating, deleting, and updating expenses.
 * - Retrieves expenses for a user based on specific time durations and calculates transaction history.
 * - Utilizes repositories to fetch expense data, and mappers to convert between entities and DTOs.
 * - Includes helper methods to get categories and process transaction data.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final TransactionMapper transactionMapper;
    private final CategoryMapper categoryMapper;
    private final UserServiceImpl userService;

    @Override
    public SummaryDto getSummary(String userName, String timeDuration) {
        UUID userId = userService.getUserIdByUserName(userName);
        LocalDate dayBeforeStartTime = DateCalculator.getStartDateByTimeDuration(timeDuration).minusDays(1);
        List<TransactionDto> transactions = findAllExpensesByUserIdAndDateAfter(userId, dayBeforeStartTime);
        Map<String, BigDecimal> summary = TransactionSummaryCalculator.calculateTransactionHistory(transactions, timeDuration);
        return SummaryDto.builder()
                .transactions(transactions)
                .categories(getCategories())
                .transactionChart(summary)
                .build();
    }

    @Override
    public void createExpense(String userName, TransactionDto dto) {
        UUID userId = userService.getUserIdByUserName(userName);
        dto.setUserId(userId);
        Expense expense = transactionMapper.dtoToExpense(dto);
        expenseRepository.save(expense);
        log.info("Expense created for user: {} with amount: {}", userName, dto.getAmount());
    }

    @Override
    public void deleteExpense(UUID expenseId) {
        expenseRepository.deleteById(expenseId);
        log.info("Expense deleted: {}", expenseId);
    }

    @Override
    public void updateExpense(String userName, TransactionDto dto) {
        UUID userId = userService.getUserIdByUserName(userName);
        dto.setUserId(userId);
        Expense existingExpense = expenseRepository.findById(dto.getTransactionId())
                .orElseThrow(() -> new EntityNotFoundException("Expense not found"));
        Expense updatedExpense = transactionMapper.dtoToExpense(dto);
        updatedExpense.setId(existingExpense.getId());
        expenseRepository.save(updatedExpense);
        log.info("Expense updated for user: {} with amount: {}", userName, dto.getAmount());
    }

    @Override
    public List<TransactionDto> findAllExpensesByUserIdAndDateAfter(UUID userId, LocalDate date) {
        return expenseRepository.findAllByUserAccountIdAndDateAfterOrderByDateDesc(userId, date)
                .stream()
                .map(transactionMapper::expenseToDto)
                .toList();
    }

    private List<CategoryDto> getCategories() {
        return expenseCategoryRepository
                .findAll()
                .stream()
                .map(categoryMapper::expenseCategoryToDto)
                .toList();
    }

}
