package com.perscolas.fintracker.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateCalculatorTest {

    @ParameterizedTest
    @CsvSource({
            "This week, 0",
            "This month, 0",
            "Last 3 months, 2"
    })
    void testGetStartDateByTimeDuration(String timeDuration, int monthsAgo) {
        LocalDate now = LocalDate.now();
        LocalDate expected;

        if (timeDuration.equals("This week")) {
            expected = now.minusDays(now.getDayOfWeek().getValue() - 1);
        } else if (timeDuration.equals("This month")) {
            expected = LocalDate.of(now.getYear(), now.getMonth(), 1);
        } else {
            expected = LocalDate.now().minusMonths(monthsAgo).withDayOfMonth(1);
        }

        LocalDate result = DateCalculator.getStartDateByTimeDuration(timeDuration);
        assertEquals(expected, result);
    }

}