package fr.teissiery.energy.domain.model;

import java.time.Duration;
import java.util.Locale;

public enum TimeStep {

    M15(Duration.ofMinutes(15)),
    HOUR(Duration.ofHours(1)),
    DAY(Duration.ofDays(1));

    private final Duration duration;


    TimeStep(Duration duration) {
        this.duration = duration;
    }

    public Duration duration() {
        return duration;
    }

    /** Parsing tolérant : "15m", "M15", "hour", "H", "day", "D" */
    public static TimeStep parse(String raw) {
        if (raw == null) throw new IllegalArgumentException("TimeStep is null");

        String s =  raw.trim().toLowerCase(Locale.ROOT);

        return switch (s) {
            case "m15", "15m" -> M15;
            case "h", "hour", "1h" -> HOUR;
            case "d", "day", "1d" -> DAY;
            default -> throw new IllegalArgumentException("Unknown TimeStep: " + raw);
        };
    }
}
