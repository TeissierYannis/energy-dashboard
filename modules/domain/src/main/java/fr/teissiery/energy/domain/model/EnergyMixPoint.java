package fr.teissiery.energy.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * Représente une mesure à un insstant t (historique)
 * Les puissances sont en MW, l'intensité carbonne en gCO2/kWh
 */
public record EnergyMixPoint(
    Instant ts,
    BigDecimal loadMw,
    BigDecimal fossilMw,
    BigDecimal nuclearMw,
    BigDecimal hydroMw,
    BigDecimal windMw,
    BigDecimal solarMw,
    BigDecimal bioMw,
    BigDecimal netImportMw,
    BigDecimal carbonIntensityGCO2PerKWh
) {
    public EnergyMixPoint {
        Objects.requireNonNull(ts, "ts");
        Objects.requireNonNull(loadMw, "loadMw");
        // Valeurs négatives interdites pour ces métriques :
        if (loadMw.signum() < 0) throw new IllegalArgumentException("loadMw < 0");
        // Les autres champsq peuvent être null si iconnus (provider partiel)
        // mais si présents, on refuse les négatifs incohérents :
        checkNonNegative("fossilMw", fossilMw);
        checkNonNegative("nuclearMw", nuclearMw);
        checkNonNegative("hydroMw", hydroMw);
        checkNonNegative("windMw", windMw);
        checkNonNegative("solarMw", solarMw);
        checkNonNegative("bioMw", bioMw);
        // netImportMw peut être négatif (export net) -> pas de check
        if (carbonIntensityGCO2PerKWh != null && carbonIntensityGCO2PerKWh.signum() < 0) {
            throw new IllegalArgumentException("carbonIntensity < 0");
        }
    }

    private static void checkNonNegative(String name, BigDecimal v) {
        if (v != null && v.signum() < 0) throw new IllegalArgumentException(name + " < 0");
    }
}
