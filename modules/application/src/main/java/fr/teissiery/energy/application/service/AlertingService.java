package fr.teissiery.energy.application.service;

import fr.teissiery.energy.domain.model.EnergyMixSnapshot;
import fr.teissiery.energy.domain.ports.AlertPublisherPort;

import java.math.BigDecimal;
import java.util.Objects;

public class AlertingService {

    private final AlertPublisherPort publisher;
    private final BigDecimal thresholdMw;
    private final BigDecimal hysteresisMw;

    private boolean armed = true;

    public AlertingService(AlertPublisherPort publisher, BigDecimal thresholdMw, BigDecimal hysteresisMw) {
        this.publisher = Objects.requireNonNull(publisher, "publisher");
        this.thresholdMw = Objects.requireNonNull(thresholdMw, "thresholdMw");
        this.hysteresisMw = hysteresisMw == null ? BigDecimal.ZERO : hysteresisMw;
        if (thresholdMw.signum() <= 0) throw new IllegalArgumentException("threshold must be > 0");
        if (this.hysteresisMw.signum() < 0) throw new IllegalArgumentException("hysteresis must be >= 0");
    }

    public void onSnapshot(EnergyMixSnapshot snapshot) {
        var load = snapshot.loadMw();
        if (load == null) return;

        // Dépassement et armé -> on envoie et on désarme
        if (armed && load.compareTo(thresholdMw) > 0) {
            publisher.publishHighLoad(snapshot);
            armed = false;
        }
        // Redescendu largement en dessous -> on réarme
        if (!armed && load.compareTo(thresholdMw.subtract(hysteresisMw)) < 0) {
            armed = true;
        }
    }

    /** pour test/monitoring */
    public boolean isArmed() {
        return armed;
    }
}
