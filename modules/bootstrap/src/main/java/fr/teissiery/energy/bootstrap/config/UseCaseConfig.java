package fr.teissiery.energy.bootstrap.config;

import fr.teissiery.energy.application.service.AlertingService;
import fr.teissiery.energy.application.usecase.GetCurrentMixUseCase;
import fr.teissiery.energy.application.usecase.GetMixHistoryUseCase;
import fr.teissiery.energy.domain.ports.AlertPublisherPort;
import fr.teissiery.energy.domain.ports.MixProviderPort;
import fr.teissiery.energy.domain.ports.MixRepositoryPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class UseCaseConfig {
    @Bean
    GetCurrentMixUseCase getCurrentMixUseCase(MixProviderPort provider) {
        return new GetCurrentMixUseCase(provider);
    }

    @Bean
    GetMixHistoryUseCase getMixHistoryUseCase(MixProviderPort provider, MixRepositoryPort repository) {
        return new GetMixHistoryUseCase(provider, repository);
    }

    @Bean
    AlertingService alertingService(
            AlertPublisherPort publisher,
            @Value("${app.alert.threshold-mw:50000}") BigDecimal thresholdMw,
            @Value("${app.alert.hysteresis-mw:2000}") BigDecimal hysteresisMw
            ) {
        return new AlertingService(publisher, thresholdMw, hysteresisMw);
    }


}
