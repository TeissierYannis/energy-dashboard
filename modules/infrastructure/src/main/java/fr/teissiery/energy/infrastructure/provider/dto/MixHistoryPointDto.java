package fr.teissiery.energy.infrastructure.provider.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record MixHistoryPointDto(Instant ts, BigDecimal loadMw) {}
