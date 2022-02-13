package ua.edu.cdu.vu.exchangeprocessor.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@UtilityClass
public class DateUtils {

    private static final ZoneId UTC = ZoneId.of("UTC");
    private static final ZoneId DEFAULT = ZoneId.systemDefault();

    public static LocalDate convertTimeZone(LocalDate date, ZoneId from, ZoneId to) {
        return ZonedDateTime.of(date, LocalTime.now(from), from)
                .withZoneSameInstant(to)
                .toLocalDate();
    }

    public static LocalDate convertTimeZone(LocalDate date) {
        return convertTimeZone(date, UTC, DEFAULT);
    }
}
