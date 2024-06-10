package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateUtil {
    private static final DateTimeFormatter DATETIMEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter DATETIMEFORMATTER_T = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public static String format(LocalDateTime localDateTime) {
        return localDateTime == null ? "" : localDateTime.format(DATETIMEFORMATTER);
    }

    public static LocalDateTime of(String str) {
        return LocalDateTime.parse(str, DATETIMEFORMATTER_T);
    }
}
