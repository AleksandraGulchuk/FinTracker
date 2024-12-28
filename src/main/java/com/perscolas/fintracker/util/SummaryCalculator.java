package com.perscolas.fintracker.util;


import com.perscolas.fintracker.model.Period;
import com.perscolas.fintracker.model.dto.transaction.TransactionDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SummaryCalculator {

    public static Map<String, BigDecimal> getGroupByCategories(List<TransactionDto> transactions) {
        Map<String, BigDecimal> map = new LinkedHashMap<>();
        for (TransactionDto transaction : transactions) {
            String category = transaction.getCategory();
            if (map.containsKey(category)) {
                map.put(category, map.get(category).add(transaction.getAmount()));
            } else {
                map.put(category, transaction.getAmount());
            }
        }
        return map;
    }

    public static Map<String, BigDecimal> getTransactionsHistory(List<TransactionDto> transactions, String period) {
        Map<String, BigDecimal> transactionsHistory;
        Period periodEnum = Period.periodOfStringValue(period);
        transactionsHistory = switch (periodEnum) {
            case WEEK -> getTransactionsSummaryByWeek(transactions, period);
            case MONTH -> getTransactionsSummaryByOneMonth(transactions);
            default -> getTransactionsSummaryByMonths(transactions, period);
        };
        return transactionsHistory;
    }

    private static Map<String, BigDecimal> getTransactionsSummaryByOneMonth(List<TransactionDto> transactions) {
        Map<String, BigDecimal> bindMap = new HashMap<>();
        bindMap.put("Week 1", BigDecimal.ZERO);
        bindMap.put("Week 2", BigDecimal.ZERO);
        bindMap.put("Week 3", BigDecimal.ZERO);
        bindMap.put("Week 4", BigDecimal.ZERO);
        for (TransactionDto transaction : transactions) {
            int dayOfMonth = transaction.getDate().getDayOfMonth();
            String unit = getWeekOfDay(dayOfMonth);
            putTransaction(transaction, bindMap, unit);
        }
        return bindMap;
    }

    private static String getWeekOfDay(int dayOfMonth) {
        if (dayOfMonth < 7) {
            return "Week 1";
        }
        if (dayOfMonth < 14) {
            return "Week 2";
        }
        if (dayOfMonth < 21) {
            return "Week 3";
        }
        return "Week 4";
    }

    private static Map<String, BigDecimal> getTransactionsSummaryByWeek(List<TransactionDto> transactions, String period) {
        Map<String, BigDecimal> bindMap = new HashMap<>();
        for (TransactionDto transaction : transactions) {
            String dayOfWeek = transaction.getDate().getDayOfWeek().toString();
            putTransaction(transaction, bindMap, dayOfWeek);
        }
        return fillEmptyDays(bindMap, period);
    }

    private static Map<String, BigDecimal> getTransactionsSummaryByMonths(List<TransactionDto> transactions, String period) {
        Map<String, BigDecimal> bindMap = new HashMap<>();
        for (TransactionDto transaction : transactions) {
            String month = transaction.getDate().getMonth().toString();
            putTransaction(transaction, bindMap, month);
        }
        return fillEmptyMonths(bindMap, period);
    }


    private static Map<String, BigDecimal> fillEmptyMonths(Map<String, BigDecimal> transactionsSummary, String period) {
        Map<String, BigDecimal> map = new LinkedHashMap<>();
        int periodLength = Period.periodOfStringValue(period).intValue;
        Month startPeriodMonth = LocalDate.now().getMonth().minus(periodLength);
        for (int i = 1; i < periodLength + 1; i++) {
            String month = startPeriodMonth.plus(i).toString();
            map.put(month, transactionsSummary.getOrDefault(month, BigDecimal.ZERO));
        }
        return map;
    }

    private static Map<String, BigDecimal> fillEmptyDays(Map<String, BigDecimal> transactionsSummary, String period) {
        Map<String, BigDecimal> map = new LinkedHashMap<>();
        LocalDate startDate = DateCalculator.getStartDateByPeriod(period);
        int periodLength = LocalDate.now().getDayOfWeek().getValue();
        for (int i = 0; i < periodLength + 1; i++) {
            String day = startDate.plusDays(i).getDayOfWeek().toString();
            map.put(day, transactionsSummary.getOrDefault(day, BigDecimal.ZERO));
        }
        return map;
    }

    private static void putTransaction(TransactionDto transaction, Map<String, BigDecimal> bindMap, String unit) {
        String type = transaction.getType();
        if (bindMap.containsKey(unit)) {
            BigDecimal total = type.equals("income")
                    ? bindMap.get(unit).add(transaction.getAmount())
                    : bindMap.get(unit).subtract(transaction.getAmount());
            bindMap.put(unit, total);
        } else {
            bindMap.put(unit, type.equals("income")
                    ? transaction.getAmount()
                    : BigDecimal.ZERO.subtract(transaction.getAmount()));
        }
    }
}
