package ru.javawebinar.topjava.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(@Nullable String dateString) {
        return DateTimeUtil.parseLocalDate(dateString);
    }
}
