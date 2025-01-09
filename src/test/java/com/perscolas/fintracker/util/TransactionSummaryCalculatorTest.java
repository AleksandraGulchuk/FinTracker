package com.perscolas.fintracker.util;

import com.perscolas.fintracker.model.dto.transaction.TransactionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TransactionSummaryCalculatorTest {

    private List<TransactionDto> transactions;

    @BeforeEach
    void setUp() {
        transactions = new ArrayList<>();
        transactions.add(new TransactionDto(UUID.randomUUID(), UUID.randomUUID(), "income", "Salary",
                UUID.randomUUID(), LocalDate.of(2025, 1, 1), BigDecimal.valueOf(1000), ""));
        transactions.add(new TransactionDto(UUID.randomUUID(), UUID.randomUUID(), "expense", "Food",
                UUID.randomUUID(), LocalDate.of(2025, 1, 2), BigDecimal.valueOf(500), ""));
        transactions.add(new TransactionDto(UUID.randomUUID(), UUID.randomUUID(), "income", "Salary",
                UUID.randomUUID(), LocalDate.of(2025, 1, 1), BigDecimal.valueOf(200), ""));
        transactions.add(new TransactionDto(UUID.randomUUID(), UUID.randomUUID(), "expense", "Food",
                UUID.randomUUID(), LocalDate.of(2025, 1, 2), BigDecimal.valueOf(150), ""));
    }

    @Test
    void testGroupTransactionsByCategory() {
        Map<String, BigDecimal> result = TransactionSummaryCalculator.groupTransactionsByCategory(transactions);
        assertEquals(2, result.size());
        assertEquals(BigDecimal.valueOf(1200), result.get("Salary"));
        assertEquals(BigDecimal.valueOf(650), result.get("Food"));
    }

    @Test
    void testCalculateTransactionHistoryForWeek() {
        String timeDuration = "This week";
        Map<String, BigDecimal> result = TransactionSummaryCalculator.calculateTransactionHistory(transactions, timeDuration);
        assertNotNull(result);
        assertEquals(7, result.size());
        assertEquals(BigDecimal.valueOf(1200), result.get("WEDNESDAY"));
        assertEquals(BigDecimal.valueOf(-650), result.get("THURSDAY"));
    }

    @Test
    void testCalculateBalanceHistoryForWeek() {
        String timeDuration = "This week";
        Map<String, BigDecimal> result = TransactionSummaryCalculator.calculateBalanceHistory(transactions, timeDuration);
        assertNotNull(result);
        assertEquals(7, result.size());
        assertEquals(BigDecimal.valueOf(1200), result.get("WEDNESDAY"));
        assertEquals(BigDecimal.valueOf(550), result.get("THURSDAY"));
    }

    @Test
    void testCalculateBalanceHistoryForMonth() {
        String timeDuration = "This month";
        Map<String, BigDecimal> result = TransactionSummaryCalculator.calculateBalanceHistory(transactions, timeDuration);
        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals(BigDecimal.valueOf(550), result.get("Days 1-7"));
    }

    @Test
    void testFilterTransactionsByType() {
        List<TransactionDto> incomeTransactions = TransactionSummaryCalculator.filterTransactionsByType(transactions, "income");
        assertEquals(2, incomeTransactions.size());

        List<TransactionDto> expenseTransactions = TransactionSummaryCalculator.filterTransactionsByType(transactions, "expense");
        assertEquals(2, expenseTransactions.size());
    }

}