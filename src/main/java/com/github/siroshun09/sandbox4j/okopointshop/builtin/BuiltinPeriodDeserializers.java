package com.github.siroshun09.sandbox4j.okopointshop.builtin;

import com.github.siroshun09.configapi.api.Configuration;
import com.github.siroshun09.sandbox4j.okopointshop.model.discount.Period;
import com.github.siroshun09.sandbox4j.okopointshop.model.registry.ConfigurationDeserializerRegistry;
import com.github.siroshun09.sandbox4j.okopointshop.util.EmptyStringChecker;
import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.EnumSet;
import java.util.Locale;

public class BuiltinPeriodDeserializers {

    public static void addToRegistry(@NotNull ConfigurationDeserializerRegistry<Period> registry) {
        registry.register(
                "datetime",
                source -> Period.datetime(
                        parseToLocalDateTime(EmptyStringChecker.isDefined(source.getString("start"), "start")),
                        parseToLocalDateTime(EmptyStringChecker.isDefined(source.getString("end"), "end")),
                        readIncludeEnd(source)
                )
        );

        registry.register(
                "date",
                source -> Period.date(
                        parseToLocalDate(EmptyStringChecker.isDefined(source.getString("start"), "start")),
                        parseToLocalDate(EmptyStringChecker.isDefined(source.getString("end"), "end")),
                        readIncludeEnd(source)
                )
        );

        registry.register(
                "time",
                source -> Period.time(
                        parseToLocalTime(EmptyStringChecker.isDefined(source.getString("start"), "start")),
                        parseToLocalTime(EmptyStringChecker.isDefined(source.getString("end"), "end")),
                        readIncludeEnd(source)
                )
        );

        registry.register("day_of_weeks", source -> Period.dayOfWeek(readDayOfWeek(source)));
    }

    private static @NotNull LocalDateTime parseToLocalDateTime(@NotNull String str) {
        try {
            return LocalDateTime.parse(str);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    private static @NotNull LocalDate parseToLocalDate(@NotNull String str) {
        try {
            return LocalDate.parse(str);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    private static @NotNull LocalTime parseToLocalTime(@NotNull String str) {
        try {
            return LocalTime.parse(str);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    private static @NotNull EnumSet<DayOfWeek> readDayOfWeek(@NotNull Configuration source) {
        var result = EnumSet.noneOf(DayOfWeek.class);

        for (var target : source.getStringList("targets")) {
            result.add(DayOfWeek.valueOf(EmptyStringChecker.isDefined(target, "day of week").toUpperCase(Locale.ENGLISH)));
        }

        return result;
    }

    private static boolean readIncludeEnd(@NotNull Configuration source) {
        return source.getBoolean("include_end");
    }
}
