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
        List<TransactionDto> incomes = getTransactionsByType(transactions, "income");
        List<TransactionDto> expenses = getTransactionsByType(transactions, "expense");

        return DashboardDto.builder()
                .expenseAmount(transactionsSummary.getExpenseAmount())
                .incomeAmount(transactionsSummary.getIncomeAmount())
                .balance(transactionsSummary.getBalance())
                .balancesChar(SummaryCalculator.getTransactionsHistory(transactions, period))
                .categoriesChar(SummaryCalculator.getGroupByCategories(expenses))
                .incomesSummary(SummaryCalculator.getTransactionsHistory(incomes, period))
                .expensesSummary(SummaryCalculator.getTransactionsHistory(expenses, period))
                .build();
    }

    private static List<TransactionDto> getTransactionsByType(List<TransactionDto> transactions, String type) {
        return transactions.stream()
                .filter(t -> t.getType().equals(type))
                .toList();
    }

}
