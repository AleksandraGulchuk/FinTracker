package com.perscolas.fintracker.servise;

import com.perscolas.fintracker.mapper.CategoryMapper;
import com.perscolas.fintracker.mapper.TransactionMapper;
import com.perscolas.fintracker.model.Period;
import com.perscolas.fintracker.model.dto.category.CategoryDto;
import com.perscolas.fintracker.model.dto.transaction.SummaryDto;
import com.perscolas.fintracker.model.dto.transaction.TransactionDto;
import com.perscolas.fintracker.model.dto.transaction.TransactionSaveDto;
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


    public SummaryDto getSummary(UUID id) {
        List<TransactionDto> transactions = getTransactions(id);
        Map<String, BigDecimal> summary = SummaryCalculator.getTransactionsSummaryByMonths(transactions, Period.SIX_MONTHS.stringValue);
        return SummaryDto.builder()
                .transactions(transactions)
                .categories(getCategories())
                .summary(summary)
                .build();
    }

    public void createIncome(TransactionSaveDto dto) {
        Income income = transactionMapper.dtoToIncome(dto);
        incomeRepository.save(income);
    }

    public void deleteIncome(UUID incomeId) {
        incomeRepository.deleteById(incomeId);
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
