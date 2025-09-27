package fr.teissiery.energy.infrastructure.provider.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record MixHistoryDto(List<MixHistoryPointDto> data) {}