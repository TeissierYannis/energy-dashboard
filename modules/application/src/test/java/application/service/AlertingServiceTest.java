package application.service;

import fr.teissiery.energy.application.service.AlertingService;
import fr.teissiery.energy.domain.model.EnergyMixSnapshot;
import fr.teissiery.energy.domain.ports.AlertPublisherPort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AlertingServiceTest {

    @Test
    void triggersOnceWithHysteresis() {
        var publisher = Mockito.mock(AlertPublisherPort.class);
        var svc = new AlertingService(publisher, new BigDecimal("50000"), new BigDecimal("2000"));

        // monte au-dessus -> déclenche 1 fois
        svc.onSnapshot(new EnergyMixSnapshot(Instant.now(), new BigDecimal("51000"),
                null,null,null,null,null,null, null, null));
        verify(publisher, times(1)).publishHighLoad(Mockito.any());

        // reste au-dessus -> pas de doublon
        svc.onSnapshot(new EnergyMixSnapshot(Instant.now(), new BigDecimal("52000"),
                null,null,null,null,null,null, null, null));
        verify(publisher, times(1)).publishHighLoad(Mockito.any());

        // redescend mais pas assez -> toujours désarmé
        svc.onSnapshot(new EnergyMixSnapshot(Instant.now(), new BigDecimal("49050"),
                null,null,null,null,null,null, null, null));
        verify(publisher, times(1)).publishHighLoad(Mockito.any());

        // redescend sous threshold - hysteresis -> on réarme
        svc.onSnapshot(new EnergyMixSnapshot(Instant.now(), new BigDecimal("47800"),
                null,null,null,null,null,null, null, null));

        // remonte au-dessus -> re-déclenche
        svc.onSnapshot(new EnergyMixSnapshot(Instant.now(), new BigDecimal("50500"),
                null,null,null,null,null,null, null, null));
        verify(publisher, times(2)).publishHighLoad(Mockito.any());
    }
}
