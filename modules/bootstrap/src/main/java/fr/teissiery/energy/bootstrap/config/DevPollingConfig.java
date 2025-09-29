package fr.teissiery.energy.bootstrap.config;

import fr.teissiery.energy.application.service.AlertingService;
import fr.teissiery.energy.application.usecase.GetCurrentMixUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "app.dev-polling.enable", havingValue = "true", matchIfMissing = false)
public class DevPollingConfig {

    private static final Logger log = LoggerFactory.getLogger(DevPollingConfig.class);

    private final GetCurrentMixUseCase getCurrent;
    private final AlertingService alerting;

    public DevPollingConfig(GetCurrentMixUseCase getCurrent, AlertingService alerting) {
        this.getCurrent = getCurrent;
        this.alerting = alerting;
    }

    /** Toutes les 10s en dev **/
    @Scheduled(fixedDelayString = "10000")
    public void pollAndAlert() {
        try {
            var s = getCurrent.execute();
            alerting.onSnapshot(s);
        } catch (Exception e) {
            log.warn("Polling Error: {}", e.getMessage());
        }
    }


}
