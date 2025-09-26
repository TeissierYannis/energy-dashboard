package fr.teissiery.energy.domain.ports;

import fr.teissiery.energy.domain.model.EnergyMixPoint;
import fr.teissiery.energy.domain.model.EnergyMixSnapshot;
import fr.teissiery.energy.domain.model.TimeStep;

import java.time.Instant;
import java.util.List;

public interface MixProviderPort {
    EnergyMixSnapshot fetchCurrent();
    List<EnergyMixPoint> fetchHistory(Instant from, Instant to, TimeStep step);
}
