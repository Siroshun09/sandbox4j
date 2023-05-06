package com.github.siroshun09.sandbox4j.okopointshop.model.discount;

import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;

public interface Period {

    Period ALWAYS = dateTime -> true;

    static @NotNull Period datetime(@NotNull LocalDateTime start, @NotNull LocalDateTime end) {
        return datetime(start, end, false);
    }

    static @NotNull Period datetime(@NotNull LocalDateTime start, @NotNull LocalDateTime end, boolean includeEnd) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException(
                    "start should be before end (start: " + DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(start) +
                            " end: " + DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(end) + ")"
            );
        }

        return dateTime ->
                (dateTime.isEqual(start) || dateTime.isAfter(start)) &&
                        (dateTime.isBefore(end) || (includeEnd && dateTime.isEqual(end)));
    }

    static @NotNull Period date(@NotNull LocalDate start, @NotNull LocalDate end) {
        return date(start, end, false);
    }

    static @NotNull Period date(@NotNull LocalDate start, @NotNull LocalDate end, boolean includeEnd) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException(
                    "start should be before end (start: " + DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(start) +
                            " end: " + DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(end) + ")"
            );
        }

        return dateTime -> {
            var date = dateTime.toLocalDate();
            return (date.isEqual(start) || date.isAfter(start)) &&
                    (date.isBefore(end) || (includeEnd && date.isEqual(end)));
        };
    }

    static @NotNull Period time(@NotNull LocalTime start, @NotNull LocalTime end) {
        return time(start, end, false);
    }

    static @NotNull Period time(@NotNull LocalTime start, @NotNull LocalTime end, boolean includeEnd) {
        if (start.isAfter(end)) {
            return dateTime -> {
                var time = dateTime.toLocalTime();
                return time.equals(start) || time.isAfter(start) ||
                        time.isBefore(end) || (includeEnd && time.equals(end));
            };
        } else {
            return dateTime -> {
                var time = dateTime.toLocalTime();
                return (time.equals(start) || time.isAfter(start)) &&
                        (time.isBefore(end) || (includeEnd && time.equals(end)));
            };
        }
    }

    static @NotNull Period dayOfWeek(@NotNull EnumSet<DayOfWeek> targets) {
        return dateTime -> targets.contains(dateTime.getDayOfWeek());
    }

    boolean isInPeriod(@NotNull LocalDateTime dateTime);

}
