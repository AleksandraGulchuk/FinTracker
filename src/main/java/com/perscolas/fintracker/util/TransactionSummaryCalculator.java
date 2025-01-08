package com.perscolas.fintracker.util;


import com.perscolas.fintracker.model.TimeDuration;
import com.perscolas.fintracker.model.dto.transaction.TransactionDto;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class TransactionSummaryCalculator {

    private static final List<String> WEEK_TIME_PERIODS = List.of(DayOfWeek.MONDAY.toString(),
            DayOfWeek.TUESDAY.toString(), DayOfWeek.WEDNESDAY.toString(), DayOfWeek.THURSDAY.toString(),
            DayOfWeek.FRIDAY.toString(), DayOfWeek.SATURDAY.toString(), DayOfWeek.SUNDAY.toString());
    private static final List<String> MONTH_TIME_PERIODS = List.of(Constants.DAYS_1_7, Constants.DAYS_8_15, Constants.DAYS_16_23, Constants.DAYS_24_END);


    public static Map<String, BigDecimal> groupTransactionsByCategory(List<TransactionDto> transactions) {
        Map<String, BigDecimal> categoryTotals = new LinkedHashMap<>();
        for (TransactionDto transaction : transactions) {
            String category = transaction.getCategory();
            if (categoryTotals.containsKey(category)) {
                categoryTotals.put(category, categoryTotals.get(category).add(transaction.getAmount()));
            } else {
                categoryTotals.put(category, transaction.getAmount());
            }
        }
        return categoryTotals;
    }

    public static Map<String, BigDecimal> calculateTransactionHistory(List<TransactionDto> transactions, String timeDuration) {
        Map<String, BigDecimal> transactionsHistory;
        TimeDuration timeDurationEnum = TimeDuration.timeDurationOfStringValue(timeDuration);
        transactionsHistory = switch (timeDurationEnum) {
            case WEEK -> summarizeTransactionsByTimePeriod(transactions, WEEK_TIME_PERIODS);
            case MONTH -> summarizeTransactionsByTimePeriod(transactions, MONTH_TIME_PERIODS);
            default -> calculateMonthlyTransactionSummary(transactions, timeDuration);
        };
        return transactionsHistory;
    }

    public static Map<String, BigDecimal> calculateBalanceHistory(List<TransactionDto> transactions, String timeDuration) {
        Map<String, BigDecimal> balanceHistory;
        TimeDuration timeDurationEnum = TimeDuration.timeDurationOfStringValue(timeDuration);
        balanceHistory = switch (timeDurationEnum) {
            case WEEK -> calculateBalanceByTimePeriod(transactions, WEEK_TIME_PERIODS);
            case MONTH -> calculateBalanceByTimePeriod(transactions, MONTH_TIME_PERIODS);
            default -> calculateMonthlyTransactionSummary(transactions, timeDuration);
        };
        return balanceHistory;
    }

    private static Map<String, BigDecimal> calculateMonthlyTransactionSummary(List<TransactionDto> transactions, String timeDuration) {
        Map<String, BigDecimal> transactionMap = initializeSummaryMap(generateTimePeriodKeys(timeDuration));
        for (TransactionDto transaction : transactions) {
            String month = transaction.getDate().getMonth().toString();
            updateTransactionSummary(transaction, transactionMap, month);
        }
        return transactionMap;
    }

    private static Map<String, BigDecimal> initializeSummaryMap(List<String> timePeriods) {
        Map<String, BigDecimal> summaryMap = new LinkedHashMap<>();
        for (String key : timePeriods) {
            summaryMap.put(key, BigDecimal.ZERO);
        }
        return summaryMap;
    }

    private static List<String> generateTimePeriodKeys(String timeDuration){
        List<String> timePeriods = new ArrayList<>();
        int timeDurationLength = TimeDuration.timeDurationOfStringValue(timeDuration).intValue;
        Month startTimeDurationMonth = LocalDate.now().getMonth().minus(timeDurationLength);
        for (int i = 1; i < timeDurationLength + 1; i++) {
            String month = startTimeDurationMonth.plus(i).toString();
            timePeriods.add(month);
        }
        return timePeriods;
    }

    private static String determineMonthPeriod(int dayOfMonth) {
        if (dayOfMonth < 8) {
            return Constants.DAYS_1_7;
        }
        if (dayOfMonth < 16) {
            return Constants.DAYS_8_15;
        }
        if (dayOfMonth < 24) {
            return Constants.DAYS_16_23;
        }
        return Constants.DAYS_24_END;
    }

    private static Map<String, BigDecimal> summarizeTransactionsByTimePeriod(List<TransactionDto> transactions, List<String> timePeriods) {
        Map<String, BigDecimal> summaryMap = initializeSummaryMap(timePeriods);
        for (TransactionDto transaction : transactions) {
            String dayOfWeek = determineTimeUnit(timePeriods, transaction);
            updateTransactionSummary(transaction, summaryMap, dayOfWeek);
        }
        return summaryMap;
    }

    private static void updateTransactionSummary(TransactionDto transaction, Map<String, BigDecimal> summaryMap, String timeUnit) {
        String type = transaction.getType();
        BigDecimal total = type.equals(Constants.INCOME)
                ? summaryMap.get(timeUnit).add(transaction.getAmount())
                : summaryMap.get(timeUnit).subtract(transaction.getAmount());
        summaryMap.put(timeUnit, total);
    }

    private static Map<String, BigDecimal> calculateBalanceByTimePeriod(List<TransactionDto> transactions, List<String> timePeriods) {
        BigDecimal balance = BigDecimal.ZERO;
        List<TransactionDto> incomes = filterTransactionsByType(transactions, Constants.INCOME);
        List<TransactionDto> expenses = filterTransactionsByType(transactions, Constants.EXPENSE);
        Map<String, BigDecimal> incomesMap = calculateTransactionsMapByType(incomes, timePeriods);
        Map<String, BigDecimal> expensesMap = calculateTransactionsMapByType(expenses, timePeriods);
        Map<String, BigDecimal> summaryMap = new LinkedHashMap<>();
        for (String day : timePeriods) {
            balance = balance.add(incomesMap.get(day)).subtract(expensesMap.get(day));
            summaryMap.put(day, balance);
        }
        return summaryMap;
    }

    public static List<TransactionDto> filterTransactionsByType(List<TransactionDto> transactions, String type) {
        return transactions.stream()
                .filter(t -> t.getType().equals(type))
                .toList();
    }

    private static Map<String, BigDecimal> calculateTransactionsMapByType(List<TransactionDto> transactions, List<String> timePeriods) {
        Map<String, BigDecimal> transactionMap = initializeSummaryMap(timePeriods);
        for (TransactionDto transaction : transactions) {
            String timeUnit = determineTimeUnit(timePeriods, transaction);
            BigDecimal total = transactionMap.get(timeUnit).add(transaction.getAmount());
            transactionMap.put(timeUnit, total);
        }
        return transactionMap;
    }

    private static String determineTimeUnit(List<String> timePeriods, TransactionDto transaction) {
        if(timePeriods.size() == 7) {
            return transaction.getDate().getDayOfWeek().toString();
        } else {
            return determineMonthPeriod(transaction.getDate().getDayOfMonth());
        }
    }
}
