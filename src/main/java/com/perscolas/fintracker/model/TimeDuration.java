package com.perscolas.fintracker.model;

public enum TimeDuration {

    WEEK("This week", 0),
    MONTH("This month", 1),
    THREE_MONTHS("Last 3 months", 3),
    SIX_MONTHS("Last 6 months", 6),
    YEAR("Last 12 months", 12);

    public final String stringValue;
    public final int intValue;

    TimeDuration(String stringValue, int intValue) {
        this.stringValue = stringValue;
        this.intValue = intValue;
    }

    public static TimeDuration timeDurationOfStringValue(String stringValue) {
        for (TimeDuration p : values()) {
            if (p.stringValue.equals(stringValue)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Invalid stringValue: " + stringValue + ". Time duration with this stringValue does not exist");
    }

}
