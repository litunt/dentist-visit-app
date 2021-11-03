package com.cgi.dentistapp.utils;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeUtils {

    private static final int VISIT_TIME = 30;

    public static boolean compare(LocalDateTime dt, LocalDate d) {
        return dt.getYear() == d.getYear()
                && dt.getMonth() == d.getMonth()
                && dt.getDayOfMonth() == d.getDayOfMonth();
    }

    public static boolean compare(LocalDateTime dt, LocalTime t) {
        return dt.getHour() == t.getHour()
                && dt.getMinute() == t.getMinute();
    }

    public static boolean timeOverlap(LocalDateTime dt, LocalTime t) {
        return dt.getHour() + 1 == t.getHour() && dt.plusMinutes(VISIT_TIME).getMinute() > t.getMinute()
                || dt.getHour() - 1 == t.getHour() && dt.minusMinutes(VISIT_TIME).getMinute() < t.getMinute()
                || dt.getHour() == t.getHour() && dt.minusMinutes(VISIT_TIME).getMinute() < t.getMinute();
    }

    public static LocalDateTime generateFromDateAndTime(LocalDate date, LocalTime time) {
        if (date == null || time == null) {
            return null;
        }
        return LocalDateTime.of(date, time);
    }

    public static boolean isBetween(LocalDate date, LocalDate start, LocalDate end) {
        boolean isAfter = start == null || (date.isAfter(start) || date.isEqual(start));
        boolean isBefore = end == null || (date.isBefore(end) || date.isEqual(end));
        return isAfter && isBefore;
    }

    public static boolean isBetween(LocalTime time, LocalTime start, LocalTime end) {
        boolean isAfter = start == null || (time.isAfter(start) || time.equals(start));
        boolean isBefore = end == null || (time.isBefore(end) || time.equals(end));
        return isAfter && isBefore;
    }
}
