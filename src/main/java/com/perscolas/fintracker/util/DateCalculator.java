package com.perscolas.fintracker.util;

import com.perscolas.fintracker.model.Period;

import java.time.LocalDate;
import java.time.Month;


public class DateCalculator {

    public static LocalDate getStartDateByPeriod(String period) {
        Period enumPeriod = Period.periodOfStringValue(period);
        LocalDate startDate = switch (enumPeriod) {
            case WEEK -> getStartDateOfThisWeek();
            case MONTH -> getStartDateOfThisMonth();
            case YEAR -> getStartDateOfThisYear();
            default -> getStartDateOfMonths(enumPeriod);
        };
        return startDate;
    }

    private static LocalDate getStartDateOfThisWeek() {
        LocalDate now = LocalDate.now();
        int dayOfWeek = now.getDayOfWeek().getValue();
        //TODO: CHECK START DATE!!!!
        return now.minusDays(dayOfWeek);
    }

    private static LocalDate getStartDateOfThisMonth() {
        LocalDate now = LocalDate.now();
        return LocalDate.of(now.getYear(), now.getMonth(), 1);
    }

    private static LocalDate getStartDateOfThisYear() {
        LocalDate now = LocalDate.now();
        return LocalDate.of(now.getYear(), 1, 1);
    }

    private static LocalDate getStartDateOfMonths(Period period) {
        LocalDate now = LocalDate.now();
        Month startPeriodMonth = now.getMonth().minus(period.intValue);
        return LocalDate.of(now.getYear(), startPeriodMonth.plus(1), 1);
    }

}
