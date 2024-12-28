package com.perscolas.fintracker.servise;

import com.perscolas.fintracker.model.dto.dashboard.DashboardDto;
import com.perscolas.fintracker.model.dto.transaction.TransactionDto;
import com.perscolas.fintracker.model.dto.transaction.TransactionsSummaryDto;
import com.perscolas.fintracker.util.SummaryCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TransactionService transactionService;

    public DashboardDto getDashboard(String userName, String period) {
        TransactionsSummaryDto transactionsSummary = transactionService.getTransactionsSummary(userName, period);
        List<TransactionDto> transactions = transactionsSummary.getTransactions();
        List<TransactionDto> expenses = getExpenses(transactions);

        return DashboardDto.builder()
                .expenseAmount(transactionsSummary.getExpenseAmount())
                .incomeAmount(transactionsSummary.getIncomeAmount())
                .balance(transactionsSummary.getBalance())
                .balancesChar(SummaryCalculator.getBalanceHistory(transactions, period))
                .categoriesChar(SummaryCalculator.getGroupByCategories(expenses))
                .build();
    }

    private static List<TransactionDto> getExpenses(List<TransactionDto> transactions) {
        return transactions.stream()
                .filter(t -> t.getType().equals("expense"))
                .toList();
    }

}
