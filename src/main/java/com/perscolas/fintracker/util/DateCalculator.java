package com.perscolas.fintracker.util;

import com.perscolas.fintracker.model.TimeDuration;

import java.time.LocalDate;

/**
 * A utility class for calculating date-related operations based on different time durations.
 * This class provides methods to determine the start date of various time periods such as the current week, month, or custom durations.
 */
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
