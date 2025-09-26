package fr.teissiery.energy.domain.ports;

import fr.teissiery.energy.domain.model.EnergyMixPoint;
import fr.teissiery.energy.domain.model.TimeStep;

import java.time.Instant;
import java.util.List;

public interface MixRepositoryPort {
    void saveAll(List<EnergyMixPoint> points);
    List<EnergyMixPoint> load(Instant from, Instant to, TimeStep step);
}
