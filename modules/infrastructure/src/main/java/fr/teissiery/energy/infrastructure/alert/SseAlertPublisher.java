package fr.teissiery.energy.infrastructure.alert;

import fr.teissiery.energy.domain.model.EnergyMixSnapshot;
import fr.teissiery.energy.domain.ports.AlertPublisherPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
public class SseAlertPublisher implements AlertPublisherPort {

    private final Sinks.Many<EnergyMixSnapshot> sink =
            Sinks.many().multicast().onBackpressureBuffer();

    @Override
    public void publishHighLoad(EnergyMixSnapshot snapshot) {
        sink.tryEmitNext(snapshot);
    }

    /** Abonnement pour l’endpoint SSE (exposé dans bootstrap) */
    public Flux<EnergyMixSnapshot> stream() {
        return sink.asFlux();
    }
}
