package com.perscolas.fintracker.util;


import com.perscolas.fintracker.model.TimeDuration;
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

    public static Map<String, BigDecimal> getTransactionsHistory(List<TransactionDto> transactions, String timeDuration) {
        Map<String, BigDecimal> transactionsHistory;
        TimeDuration timeDurationEnum = TimeDuration.timeDurationOfStringValue(timeDuration);
        transactionsHistory = switch (timeDurationEnum) {
            case WEEK -> getTransactionsSummaryByWeek(transactions, timeDuration);
            case MONTH -> getTransactionsSummaryByOneMonth(transactions);
            default -> getTransactionsSummaryByMonths(transactions, timeDuration);
        };
        return transactionsHistory;
    }

    private static Map<String, BigDecimal> getTransactionsSummaryByOneMonth(List<TransactionDto> transactions) {
        Map<String, BigDecimal> bindMap = getOneMonthMap();
        for (TransactionDto transaction : transactions) {
            int dayOfMonth = transaction.getDate().getDayOfMonth();
            String unit = getWeekOfDay(dayOfMonth);
            putTransaction(transaction, bindMap, unit);
        }
        return bindMap;
    }

    private static Map<String, BigDecimal> getOneMonthMap() {
        Map<String, BigDecimal> bindMap = new HashMap<>();
        bindMap.put(Constants.WEEK_ONE, BigDecimal.ZERO);
        bindMap.put(Constants.WEEK_TWO, BigDecimal.ZERO);
        bindMap.put(Constants.WEEK_THREE, BigDecimal.ZERO);
        bindMap.put(Constants.WEEK_FOUR, BigDecimal.ZERO);
        return bindMap;
    }

    private static String getWeekOfDay(int dayOfMonth) {
        if (dayOfMonth < 7) {
            return Constants.WEEK_ONE;
        }
        if (dayOfMonth < 14) {
            return Constants.WEEK_TWO;
        }
        if (dayOfMonth < 21) {
            return Constants.WEEK_THREE;
        }
        return Constants.WEEK_FOUR;
    }

    private static Map<String, BigDecimal> getTransactionsSummaryByWeek(List<TransactionDto> transactions, String timeDuration) {
        Map<String, BigDecimal> bindMap = new HashMap<>();
        for (TransactionDto transaction : transactions) {
            String dayOfWeek = transaction.getDate().getDayOfWeek().toString();
            putTransaction(transaction, bindMap, dayOfWeek);
        }
        return fillEmptyDays(bindMap, timeDuration);
    }

    private static Map<String, BigDecimal> getTransactionsSummaryByMonths(List<TransactionDto> transactions, String timeDuration) {
        Map<String, BigDecimal> bindMap = new HashMap<>();
        for (TransactionDto transaction : transactions) {
            String month = transaction.getDate().getMonth().toString();
            putTransaction(transaction, bindMap, month);
        }
        return fillEmptyMonths(bindMap, timeDuration);
    }


    private static Map<String, BigDecimal> fillEmptyMonths(Map<String, BigDecimal> transactionsSummary, String timeDuration) {
        Map<String, BigDecimal> map = new LinkedHashMap<>();
        int timeDurationLength = TimeDuration.timeDurationOfStringValue(timeDuration).intValue;
        Month startTimeDurationMonth = LocalDate.now().getMonth().minus(timeDurationLength);
        for (int i = 1; i < timeDurationLength + 1; i++) {
            String month = startTimeDurationMonth.plus(i).toString();
            map.put(month, transactionsSummary.getOrDefault(month, BigDecimal.ZERO));
        }
        return map;
    }

    private static Map<String, BigDecimal> fillEmptyDays(Map<String, BigDecimal> transactionsSummary, String timeDuration) {
        Map<String, BigDecimal> map = new LinkedHashMap<>();
        LocalDate startDate = DateCalculator.getStartDateByTimeDuration(timeDuration);
        int timeDurationLength = LocalDate.now().getDayOfWeek().getValue();
        for (int i = 0; i < timeDurationLength + 1; i++) {
            String day = startDate.plusDays(i).getDayOfWeek().toString();
            map.put(day, transactionsSummary.getOrDefault(day, BigDecimal.ZERO));
        }
        return map;
    }

    private static void putTransaction(TransactionDto transaction, Map<String, BigDecimal> bindMap, String unit) {
        String type = transaction.getType();
        if (bindMap.containsKey(unit)) {
            BigDecimal total = type.equals(Constants.INCOME)
                    ? bindMap.get(unit).add(transaction.getAmount())
                    : bindMap.get(unit).subtract(transaction.getAmount());
            bindMap.put(unit, total);
        } else {
            bindMap.put(unit, type.equals(Constants.INCOME)
                    ? transaction.getAmount()
                    : BigDecimal.ZERO.subtract(transaction.getAmount()));
        }
    }
}
