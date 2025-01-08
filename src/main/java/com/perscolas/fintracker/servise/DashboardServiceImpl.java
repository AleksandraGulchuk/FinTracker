package com.perscolas.fintracker.servise;

import com.perscolas.fintracker.model.dto.dashboard.DashboardDto;
import com.perscolas.fintracker.model.dto.transaction.TransactionDto;
import com.perscolas.fintracker.model.dto.transaction.TransactionsSummaryDto;
import com.perscolas.fintracker.servise.interfaces.DashboardService;
import com.perscolas.fintracker.util.Constants;
import com.perscolas.fintracker.util.TransactionSummaryCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final TransactionServiceImpl transactionService;

    @Override
    public DashboardDto generateDashboardSummary(String userName, String timeDuration) {
        TransactionsSummaryDto transactionsSummary = transactionService.getTransactionsSummary(userName, timeDuration);
        List<TransactionDto> transactions = transactionsSummary.getTransactions();
        List<TransactionDto> incomes = TransactionSummaryCalculator.filterTransactionsByType(transactions, Constants.INCOME);
        List<TransactionDto> expenses = TransactionSummaryCalculator.filterTransactionsByType(transactions, Constants.EXPENSE);

        return DashboardDto.builder()
                .expenseAmount(transactionsSummary.getExpenseAmount())
                .incomeAmount(transactionsSummary.getIncomeAmount())
                .balance(transactionsSummary.getBalance())
                .balancesChart(TransactionSummaryCalculator.calculateBalanceHistory(transactions, timeDuration))
                .expenseCategoriesChart(TransactionSummaryCalculator.groupTransactionsByCategory(expenses))
                .incomeCategoriesChart(TransactionSummaryCalculator.groupTransactionsByCategory(incomes))
                .incomesChart(TransactionSummaryCalculator.calculateTransactionHistory(incomes, timeDuration))
                .expensesChart(TransactionSummaryCalculator.calculateTransactionHistory(expenses, timeDuration))
                .build();
    }

}
