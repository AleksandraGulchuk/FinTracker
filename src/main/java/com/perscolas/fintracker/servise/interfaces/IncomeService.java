package com.perscolas.fintracker.servise.interfaces;

import com.perscolas.fintracker.model.dto.transaction.SummaryDto;
import com.perscolas.fintracker.model.dto.transaction.TransactionDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IncomeService {

    SummaryDto getSummary(String userName, String timeDuration);

    void createIncome(String userName, TransactionDto transaction);

    void deleteIncome(UUID incomeId);

    void updateIncome(String userName, TransactionDto transaction);

    List<TransactionDto> findAllIncomesByUserIdAndDateAfter(UUID userId, LocalDate date);
}
