package com.perscolas.fintracker.servise;

import com.perscolas.fintracker.model.dto.dashboard.DashboardDto;
import com.perscolas.fintracker.model.dto.transaction.TransactionDto;
import com.perscolas.fintracker.model.dto.transaction.TransactionsSummaryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

class DashboardServiceImplTest {

    @Mock
    private TransactionServiceImpl transactionService;

    @InjectMocks
    private DashboardServiceImpl dashboardService;

    private List<TransactionDto> transactions;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
    void testGenerateDashboardSummary() {
        TransactionsSummaryDto summaryDto = TransactionsSummaryDto.builder()
                .incomeAmount(BigDecimal.valueOf(1200))
                .expenseAmount(BigDecimal.valueOf(650))
                .balance(BigDecimal.valueOf(550))
                .transactions(transactions)
                .build();
        when(transactionService.getTransactionsSummary(anyString(), anyString())).thenReturn(summaryDto);

        DashboardDto result = dashboardService.generateDashboardSummary("testUser", "This month");

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(1200), result.getIncomeAmount());
        assertEquals(BigDecimal.valueOf(650), result.getExpenseAmount());
        assertEquals(BigDecimal.valueOf(550), result.getBalance());

        assertNotNull(result.getBalancesChart());
        assertNotNull(result.getExpenseCategoriesChart());
        assertNotNull(result.getIncomeCategoriesChart());
        assertNotNull(result.getIncomesChart());
        assertNotNull(result.getExpensesChart());
    }

    @Test
    void testGenerateDashboardSummaryWithNoTransactions() {
        TransactionsSummaryDto summaryDto = TransactionsSummaryDto.builder()
                .incomeAmount(BigDecimal.ZERO)
                .expenseAmount(BigDecimal.ZERO)
                .balance(BigDecimal.ZERO)
                .transactions(new ArrayList<>())
                .build();

        when(transactionService.getTransactionsSummary(anyString(), anyString())).thenReturn(summaryDto);

        DashboardDto result = dashboardService.generateDashboardSummary("testUser", "This month");

        assertNotNull(result);
        assertEquals(BigDecimal.ZERO, result.getExpenseAmount());
        assertEquals(BigDecimal.ZERO, result.getIncomeAmount());
        assertEquals(BigDecimal.ZERO, result.getBalance());
    }
}