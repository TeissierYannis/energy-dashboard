package fr.teissiery.energy.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Représente l'insstantané du mix à l'insstant courant (ou très récent)
 * Même structure qu'un point historique, mais sémantique différente.
 */
public record EnergyMixSnapshot(
        Instant ts,
        BigDecimal loadMw,
        BigDecimal fossilMw,
        BigDecimal nuclearMw,
        BigDecimal hydroMw,
        BigDecimal windMw,
        BigDecimal solarMw,
        BigDecimal bioMw,
        BigDecimal netImportMw,
        BigDecimal carbonIntensityGCo2PerKwh
) {
    public EnergyMixSnapshot {
        // Réutilise la logique de validation du point historique
        // en profiant du consstructur de EnergyMixPoint
        new EnergyMixPoint(
                ts,
                loadMw,
                fossilMw,
                nuclearMw,
                hydroMw,
                windMw,
                solarMw,
                bioMw,
                netImportMw,
                carbonIntensityGCo2PerKwh
        );
    }
}
