package fr.teissiery.energy.bootstrap.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Instant;

@Schema(description = "Instantané du mix")
public record EnergyMixSnapshotDto(
        Instant ts,
        BigDecimal loadMw,
        BigDecimal carbonIntensityGCo2PerKwh
) {}

