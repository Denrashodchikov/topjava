package ru.javawebinar.topjava.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalTime;

public class StringToLocalTimeConverter implements Converter<String, LocalTime> {

    @Override
    public LocalTime convert(@Nullable String timeString) {
        return DateTimeUtil.parseLocalTime(timeString);
    }
}