package fr.teissiery.energy.bootstrap.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Instant;

@Schema(description = "Point historique (minimal)")
public record EnergyMixPointDto(
        Instant ts,
        BigDecimal loadMw
) {}
