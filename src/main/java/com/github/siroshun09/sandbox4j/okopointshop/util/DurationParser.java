package com.github.siroshun09.sandbox4j.okopointshop.util;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Parses durations from a string format
 * <p>
 * Original: <a href="https://github.com/LuckPerms/LuckPerms/blob/master/common/src/main/java/me/lucko/luckperms/common/util/DurationParser.java">LuckPerms DurationParser</a>
 */
public final class DurationParser {
    private DurationParser() {
    }

    private static final ChronoUnit[] UNITS;
    private static final Pattern PATTERN;

    static {
        var unitPatterns = Map.of(
                ChronoUnit.YEARS, "y(?:ear,?s?",
                ChronoUnit.MONTHS, "mo(?:nth,?s?",
                ChronoUnit.WEEKS, "w(?:eek,?s?",
                ChronoUnit.DAYS, "d(?:ay,?s?",
                ChronoUnit.HOURS, "h(?:our|r,?s?",
                ChronoUnit.MINUTES, "m(?:inute|in,?s?",
                ChronoUnit.SECONDS, "s(?:econd|ec,?s?"
        );

        UNITS = unitPatterns.keySet().toArray(new ChronoUnit[0]);

        var strPattern =
                unitPatterns.values().stream()
                        .map(pattern -> "(?:(\\d+)\\s*" + pattern + "[,\\s]*)?")
                        .collect(Collectors.joining("", "^\\s*", "$"));

        PATTERN = Pattern.compile(strPattern, Pattern.CASE_INSENSITIVE);
    }

    public static Duration parseDuration(String input) throws IllegalArgumentException {
        Matcher matcher = PATTERN.matcher(input);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("unable to parse duration: " + input);
        }

        Duration duration = Duration.ZERO;
        for (int i = 0; i < UNITS.length; i++) {
            ChronoUnit unit = UNITS[i];
            var group = matcher.group(i + 1);

            if (group != null && !group.isEmpty()) {
                int n = Integer.parseInt(group);
                if (n > 0) {
                    duration = duration.plus(unit.getDuration().multipliedBy(n));
                }
            }
        }

        return duration;
    }
}