package fr.teissiery.energy.bootstrap.api;

import fr.teissiery.energy.infrastructure.alert.SseAlertPublisher;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;

@Tag(name = "Alerts")
@RestController
public class AlertController {

    private final SseAlertPublisher publisher;

    public AlertController(SseAlertPublisher publisher) {
        this.publisher = publisher;
    }

    @Operation(summary = "Flux d'alertes haute chartge (SSE)")
    @GetMapping(path = "/api/alerts/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<?> stream() {
        return publisher.stream();
    }
}
