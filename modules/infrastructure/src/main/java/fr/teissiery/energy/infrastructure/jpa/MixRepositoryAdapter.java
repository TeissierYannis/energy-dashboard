package fr.teissiery.energy.infrastructure.jpa;

import fr.teissiery.energy.domain.model.EnergyMixPoint;
import fr.teissiery.energy.domain.model.TimeStep;
import fr.teissiery.energy.domain.ports.MixRepositoryPort;
import fr.teissiery.energy.infrastructure.mapper.EnergyMixMapper;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public class MixRepositoryAdapter implements MixRepositoryPort {

    private final EnergyMixPointJpaRepository repo;
    private final EnergyMixMapper mapper;

    public MixRepositoryAdapter(EnergyMixPointJpaRepository repo, EnergyMixMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public void saveAll(List<EnergyMixPoint> points) {
        var entities = points.stream().map(mapper::toEntity).toList();
        repo.saveAll(entities);
    }

    @Override
    public List<EnergyMixPoint> load(Instant from, Instant to, TimeStep step) {
        // TODO: l’agrégation par step pourra être faite via SQL (date_trunc) plus tard.
        return repo.findRange(from, to).stream().map(mapper::toDomain).toList();
    }
}
