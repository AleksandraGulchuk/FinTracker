package com.perscolas.fintracker.servise;

import com.perscolas.fintracker.model.dto.dashboard.DashboardDto;
import com.perscolas.fintracker.model.dto.transaction.TransactionDto;
import com.perscolas.fintracker.model.dto.transaction.TransactionsSummaryDto;
import com.perscolas.fintracker.util.Constants;
import com.perscolas.fintracker.util.SummaryCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TransactionService transactionService;

    public DashboardDto getDashboard(String userName, String timeDuration) {
        TransactionsSummaryDto transactionsSummary = transactionService.getTransactionsSummary(userName, timeDuration);
        List<TransactionDto> transactions = transactionsSummary.getTransactions();
        List<TransactionDto> incomes = getTransactionsByType(transactions, Constants.INCOME);
        List<TransactionDto> expenses = getTransactionsByType(transactions, Constants.EXPENSE);

        return DashboardDto.builder()
                .expenseAmount(transactionsSummary.getExpenseAmount())
                .incomeAmount(transactionsSummary.getIncomeAmount())
                .balance(transactionsSummary.getBalance())
                .balancesChart(SummaryCalculator.calculateBalanceHistory(transactions, timeDuration))
                .expenseCategoriesChart(SummaryCalculator.groupTransactionsByCategory(expenses))
                .incomeCategoriesChart(SummaryCalculator.groupTransactionsByCategory(incomes))
                .incomesChart(SummaryCalculator.calculateTransactionHistory(incomes, timeDuration))
                .expensesChart(SummaryCalculator.calculateTransactionHistory(expenses, timeDuration))
                .build();
    }

    private static List<TransactionDto> getTransactionsByType(List<TransactionDto> transactions, String type) {
        return transactions.stream()
                .filter(t -> t.getType().equals(type))
                .toList();
    }

}
