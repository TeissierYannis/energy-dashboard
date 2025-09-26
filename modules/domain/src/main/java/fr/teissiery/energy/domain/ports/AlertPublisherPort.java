package fr.teissiery.energy.domain.ports;

import fr.teissiery.energy.domain.model.EnergyMixSnapshot;

public interface AlertPublisherPort {
    void publishHighLoad(EnergyMixSnapshot snapshot);
}
