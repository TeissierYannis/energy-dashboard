package fr.teissiery.energy.application.usecase;

import fr.teissiery.energy.domain.model.EnergyMixPoint;
import fr.teissiery.energy.domain.model.TimeStep;
import fr.teissiery.energy.domain.ports.MixProviderPort;
import fr.teissiery.energy.domain.ports.MixRepositoryPort;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class GetMixHistoryUseCase {

    private final MixProviderPort provider;
    private final MixRepositoryPort repository;

    public GetMixHistoryUseCase(MixProviderPort provider, MixRepositoryPort repository) {
        this.provider = Objects.requireNonNull(provider, "provider");
        this.repository = Objects.requireNonNull(repository, "repository");
    }

    public List<EnergyMixPoint> execute(Instant from, Instant to, TimeStep step) {
        if (from == null || to == null || step == null) {
            throw new IllegalArgumentException("from/to/step must not be null");
        }
        if (!from.isBefore(to)) {
            throw new IllegalArgumentException("from must be before to");
        }

        var points = provider.fetchHistory(from, to, step);
        if (points != null && !points.isEmpty()) {
            repository.saveAll(points);
        }

        return points;
    }
}
