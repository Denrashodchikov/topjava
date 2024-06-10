package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateUtil {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static String convert(LocalDateTime localDateTime) {
        return localDateTime == null ? "" : localDateTime.format(dateTimeFormatter);
    }
}
