package com.perscolas.fintracker.servise;

import com.perscolas.fintracker.model.dto.transaction.TransactionDto;
import com.perscolas.fintracker.model.dto.transaction.TransactionsSummaryDto;
import com.perscolas.fintracker.servise.interfaces.TransactionService;
import com.perscolas.fintracker.util.DateCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Implementation of the TransactionService interface that manages transactions.
 * - Retrieves and combines expenses and incomes for a user within a specified time duration.
 * - Calculates the total expenses, incomes, and balance for the user.
 * - Provides a sorted list of transactions (expenses and incomes) for the user.
 */
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final ExpenseServiceImpl expenseService;
    private final IncomeServiceImpl incomeService;
    private final UserServiceImpl userService;

    @Override
    public TransactionsSummaryDto getTransactionsSummary(String userName, String timeDuration) {
        UUID userId = userService.getUserIdByUserName(userName);
        LocalDate dayBeforeStartTime = DateCalculator.getStartDateByTimeDuration(timeDuration).minusDays(1);

        List<TransactionDto> expenses = expenseService.findAllExpensesByUserIdAndDateAfter(userId, dayBeforeStartTime);
        List<TransactionDto> incomes = incomeService.findAllIncomesByUserIdAndDateAfter(userId, dayBeforeStartTime);
        List<TransactionDto> transactions = concatTransactions(expenses, incomes);

        BigDecimal expenseAmount = getTransactionAmount(expenses);
        BigDecimal incomeAmount = getTransactionAmount(incomes);
        BigDecimal balance = incomeAmount.subtract(expenseAmount);

        return TransactionsSummaryDto.builder()
                .expenseAmount(expenseAmount)
                .incomeAmount(incomeAmount)
                .balance(balance)
                .transactions(transactions)
                .build();
    }

    private static List<TransactionDto> concatTransactions(List<TransactionDto> expenses, List<TransactionDto> incomes) {
        return Stream
                .concat(expenses.stream(), incomes.stream())
                .sorted(Comparator.comparing(TransactionDto::getDate).reversed())
                .toList();
    }

    private static BigDecimal getTransactionAmount(List<TransactionDto> transactions) {
        return transactions.stream()
                .map(TransactionDto::getAmount)
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal(0));
    }

}
