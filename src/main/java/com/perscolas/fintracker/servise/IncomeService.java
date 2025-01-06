package com.perscolas.fintracker.servise;

import com.perscolas.fintracker.mapper.CategoryMapper;
import com.perscolas.fintracker.mapper.TransactionMapper;
import com.perscolas.fintracker.model.dto.category.CategoryDto;
import com.perscolas.fintracker.model.dto.transaction.SummaryDto;
import com.perscolas.fintracker.model.dto.transaction.TransactionDto;
import com.perscolas.fintracker.model.entity.Income;
import com.perscolas.fintracker.repository.IncomeCategoryRepository;
import com.perscolas.fintracker.repository.IncomeRepository;
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
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final IncomeCategoryRepository incomeCategoryRepository;
    private final TransactionMapper transactionMapper;
    private final CategoryMapper categoryMapper;
    private final UserService userService;


    public SummaryDto getSummary(String userName, String timeDuration) {
        UUID userId = userService.getUserIdByUserName(userName);
        List<TransactionDto> transactions = getTransactions(userId);
        Map<String, BigDecimal> summary = SummaryCalculator.getTransactionsHistory(transactions, timeDuration);
        return SummaryDto.builder()
                .transactions(transactions)
                .categories(getCategories())
                .transactionChart(summary)
                .build();
    }

    public void createIncome(String userName, TransactionDto transaction) {
        UUID userId = userService.getUserIdByUserName(userName);
        transaction.setUserId(userId);
        Income income = transactionMapper.dtoToIncome(transaction);
        incomeRepository.save(income);
    }

    public void deleteIncome(UUID incomeId) {
        incomeRepository.deleteById(incomeId);
    }

    public void updateIncome(String userName, TransactionDto transaction) {
        UUID userId = userService.getUserIdByUserName(userName);
        transaction.setUserId(userId);
        Income income = transactionMapper.dtoToIncome(transaction);
        incomeRepository.save(income);
    }

    public List<Income> findAllIncomesByUserIdAndDateAfter(UUID userId, LocalDate date) {
        return incomeRepository.findAllByUserAccountIdAndDateAfterOrderByDateDesc(userId, date);
    }

    private List<CategoryDto> getCategories() {
        return incomeCategoryRepository
                .findAll()
                .stream()
                .map(categoryMapper::incomeCategoryToDto)
                .toList();
    }

    private List<TransactionDto> getTransactions(UUID id) {
        return incomeRepository
                .findAllByUserAccountIdOrderByDateDesc(id)
                .stream()
                .map(transactionMapper::incomeToDto)
                .toList();
    }
}
