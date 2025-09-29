package fr.teissiery.energy.bootstrap.api;

import fr.teissiery.energy.application.usecase.GetCurrentMixUseCase;
import fr.teissiery.energy.application.usecase.GetMixHistoryUseCase;
import fr.teissiery.energy.bootstrap.api.dto.EnergyMixPointDto;
import fr.teissiery.energy.bootstrap.api.dto.EnergyMixSnapshotDto;
import fr.teissiery.energy.domain.model.TimeStep;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;

@Tag(name = "Energy Mix")
@RestController
@RequestMapping("/api/mix")
public class MixController {

    private final GetCurrentMixUseCase getCurrent;
    private final GetMixHistoryUseCase getHistory;

    public MixController(GetCurrentMixUseCase getCurrent, GetMixHistoryUseCase getHistory) {
        this.getCurrent = getCurrent;
        this.getHistory = getHistory;
    }

    @Operation(summary = "Instantané du mix actuel")
    @GetMapping("/current")
    public Mono<EnergyMixSnapshotDto> current() {
        var s = getCurrent.execute();
        return Mono.just(new EnergyMixSnapshotDto(
                s.ts(),
                s.loadMw(),
                s.carbonIntensityGCo2PerKwh()
        ));
    }

    @Operation(summary = "Historique du mix")
    @GetMapping("/history")
    public Mono<List<EnergyMixPointDto>> history(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Instant from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Instant to,
            @RequestParam String step
            ) {
        var points = getHistory.execute(from, to, TimeStep.parse(step));
        return Mono.just(points.stream()
                .map(p -> new EnergyMixPointDto(p.ts(), p.loadMw()))
                .toList());
    }
}
