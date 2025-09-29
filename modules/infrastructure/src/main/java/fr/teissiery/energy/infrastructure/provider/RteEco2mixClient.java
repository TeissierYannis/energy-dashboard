package fr.teissiery.energy.infrastructure.provider;

import fr.teissiery.energy.domain.model.EnergyMixPoint;
import fr.teissiery.energy.domain.model.EnergyMixSnapshot;
import fr.teissiery.energy.domain.model.TimeStep;
import fr.teissiery.energy.domain.ports.MixProviderPort;
import fr.teissiery.energy.infrastructure.provider.dto.MixCurrentDto;
import fr.teissiery.energy.infrastructure.provider.dto.MixHistoryDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Component
public class RteEco2mixClient implements MixProviderPort {

    private final WebClient http;

    public RteEco2mixClient(WebClient http) {
        this.http = http;
    }

    @Override
    @Retry(name = "rte")
    @CircuitBreaker(name = "rte")
    public EnergyMixSnapshot fetchCurrent() {
        var dto = http.get().uri("/mix/current")
                .retrieve().bodyToMono(MixCurrentDto.class)
                .block();
        if (dto == null) throw new IllegalStateException("Empty response");
        return new EnergyMixSnapshot(
                dto.ts(),
                dto.loadMw(),
                null, null, null, null, null, null,
                null,
                dto.carbonIntensity()
        );
    }

    @Override
    @Retry(name = "rte")
    @CircuitBreaker(name = "rte")
    public List<EnergyMixPoint> fetchHistory(Instant from, Instant to, TimeStep step) {
        var dto = http.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/mix/history")
                        .queryParam("from", from.toString())
                        .queryParam("to", to.toString())
                        .queryParam("step", step.name())
                        .build())
                .retrieve()
                .bodyToMono(MixHistoryDto.class)
                .block();
        if (dto == null || dto.data() == null) return List.of();
        return dto.data().stream()
                .map(p -> new EnergyMixPoint(
                        p.ts(), p.loadMw(),
                        null, null, null, null, null, null,
                        null, null))
                .toList();
    }
}
