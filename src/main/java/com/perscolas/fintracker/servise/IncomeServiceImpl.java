package com.perscolas.fintracker.servise;

import com.perscolas.fintracker.exception.EntityNotFoundException;
import com.perscolas.fintracker.model.mapper.CategoryMapper;
import com.perscolas.fintracker.model.mapper.TransactionMapper;
import com.perscolas.fintracker.model.dto.category.CategoryDto;
import com.perscolas.fintracker.model.dto.transaction.SummaryDto;
import com.perscolas.fintracker.model.dto.transaction.TransactionDto;
import com.perscolas.fintracker.model.entity.Income;
import com.perscolas.fintracker.repository.IncomeCategoryRepository;
import com.perscolas.fintracker.repository.IncomeRepository;
import com.perscolas.fintracker.servise.interfaces.IncomeService;
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
 * Implementation of the IncomeService interface.
 * - Provides methods to manage incomes, including getting summaries, creating, deleting, and updating incomes.
 * - Retrieves incomes for a user based on specific time durations and calculates transaction history.
 * - Utilizes repositories to fetch income data, and mappers to convert between entities and DTOs.
 * - Includes helper methods to get categories and process transaction data.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final IncomeCategoryRepository incomeCategoryRepository;
    private final TransactionMapper transactionMapper;
    private final CategoryMapper categoryMapper;
    private final UserServiceImpl userService;

    @Override
    public SummaryDto getSummary(String userName, String timeDuration) {
        UUID userId = userService.getUserIdByUserName(userName);
        LocalDate dayBeforeStartTime = DateCalculator.getStartDateByTimeDuration(timeDuration).minusDays(1);
        List<TransactionDto> transactions = findAllIncomesByUserIdAndDateAfter(userId, dayBeforeStartTime);
        Map<String, BigDecimal> summary = TransactionSummaryCalculator.calculateTransactionHistory(transactions, timeDuration);
        return SummaryDto.builder()
                .transactions(transactions)
                .categories(getCategories())
                .transactionChart(summary)
                .build();
    }

    @Override
    public void createIncome(String userName, TransactionDto transaction) {
        UUID userId = userService.getUserIdByUserName(userName);
        transaction.setUserId(userId);
        Income income = transactionMapper.dtoToIncome(transaction);
        incomeRepository.save(income);
    }

    @Override
    public void deleteIncome(UUID incomeId) {
        incomeRepository.deleteById(incomeId);
    }

    @Override
    public void updateIncome(String userName, TransactionDto transaction) {
        Income income = transactionMapper.dtoToIncome(transaction);
        incomeRepository.save(income);
        UUID userId = userService.getUserIdByUserName(userName);
        transaction.setUserId(userId);
        Income existingIncome = incomeRepository.findById(transaction.getTransactionId())
                .orElseThrow(() -> new EntityNotFoundException("Income not found"));
        Income updatedIncome = transactionMapper.dtoToIncome(transaction);
        updatedIncome.setId(existingIncome.getId());
        incomeRepository.save(updatedIncome);
    }

    @Override
    public List<TransactionDto> findAllIncomesByUserIdAndDateAfter(UUID userId, LocalDate date) {
        return incomeRepository
                .findAllByUserAccountIdAndDateAfterOrderByDateDesc(userId, date)
                .stream()
                .map(transactionMapper::incomeToDto)
                .toList();
    }

    private List<CategoryDto> getCategories() {
        return incomeCategoryRepository
                .findAll()
                .stream()
                .map(categoryMapper::incomeCategoryToDto)
                .toList();
    }

}
