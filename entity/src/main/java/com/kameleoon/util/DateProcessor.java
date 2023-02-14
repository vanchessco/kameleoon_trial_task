package com.kameleoon.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateProcessor {

    public static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static LocalDateTime toDate(final String date) {
        return LocalDateTime.parse(date, formatter);
    }

    public static String toString(final LocalDateTime date) {
        return date.format(formatter);
    }

}
