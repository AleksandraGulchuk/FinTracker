package com.perscolas.fintracker.servise;

import com.perscolas.fintracker.mapper.TransactionMapper;
import com.perscolas.fintracker.model.dto.transaction.TransactionDto;
import com.perscolas.fintracker.model.dto.transaction.TransactionsSummaryDto;
import com.perscolas.fintracker.util.DateCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final ExpenseService expenseService;
    private final IncomeService incomeService;
    private final TransactionMapper transactionMapper;
    private final UserService userService;


    public TransactionsSummaryDto getTransactionsSummary(String userName, String period) {
        UUID userId = userService.getUserIdByUserName(userName);
        LocalDate date = DateCalculator.getStartDateByPeriod(period).minusDays(1);

        List<TransactionDto> expensesForPeriod = transactionMapper
                .expensesToDto(expenseService.findAllExpensesByUserIdAndDateAfter(userId, date));
        List<TransactionDto> incomesForPeriod = transactionMapper
                .incomesToDto(incomeService.findAllIncomesByUserIdAndDateAfter(userId, date));
        List<TransactionDto> transactions = concatTransactions(expensesForPeriod, incomesForPeriod);

        BigDecimal expenseAmount = getTransactionAmount(expensesForPeriod);
        BigDecimal incomeAmount = getTransactionAmount(incomesForPeriod);
        BigDecimal balance = incomeAmount.subtract(expenseAmount);

        return TransactionsSummaryDto.builder()
                .expenseAmount(expenseAmount)
                .incomeAmount(incomeAmount)
                .balance(balance)
                .transactions(transactions)
                .build();
    }

    private static List<TransactionDto> concatTransactions(List<TransactionDto> expensesForPeriod, List<TransactionDto> incomesForPeriod) {
        return Stream
                .concat(expensesForPeriod.stream(), incomesForPeriod.stream())
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
