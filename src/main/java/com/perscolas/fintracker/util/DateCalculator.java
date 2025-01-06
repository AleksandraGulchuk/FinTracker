package com.perscolas.fintracker.util;

import com.perscolas.fintracker.model.TimeDuration;

import java.time.LocalDate;


public class DateCalculator {

    public static LocalDate getStartDateByTimeDuration(String timeDuration) {
        TimeDuration enumTimeDuration = TimeDuration.timeDurationOfStringValue(timeDuration);
        LocalDate startDate = switch (enumTimeDuration) {
            case WEEK -> getStartDateOfThisWeek();
            case MONTH -> getStartDateOfThisMonth();
            default -> getStartDateOfMonths(enumTimeDuration);
        };
        return startDate;
    }

    private static LocalDate getStartDateOfThisWeek() {
        LocalDate now = LocalDate.now();
        int dayOfWeek = now.getDayOfWeek().getValue();
        //TODO: Fix START DATE for Sunday!!!!
        return now.minusDays(dayOfWeek - 1);
    }

    private static LocalDate getStartDateOfThisMonth() {
        LocalDate now = LocalDate.now();
        return LocalDate.of(now.getYear(), now.getMonth(), 1);
    }

    private static LocalDate getStartDateOfMonths(TimeDuration timeDuration) {
        LocalDate startTimeDuration = LocalDate.now().minusMonths(timeDuration.intValue);
        return LocalDate.of(startTimeDuration.getYear(), startTimeDuration.getMonth().plus(1), 1);
    }

}
