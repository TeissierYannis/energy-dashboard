package fr.teissiery.energy.application.usecase;

import fr.teissiery.energy.domain.ports.MixProviderPort;
import fr.teissiery.energy.domain.model.EnergyMixSnapshot;
import java.util.Objects;

/** Cas d'usage : Récupérer l'instantané actuel du mix */
public class GetCurrentMixUseCase {

    private final MixProviderPort provider;

    public GetCurrentMixUseCase(MixProviderPort provider) {
        this.provider = Objects.requireNonNull(provider, "provider");
    }

    public EnergyMixSnapshot execute() {
        return provider.fetchCurrent();
    }
}
