package fr.teissiery.energy.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(@Value("${providers.rte.base-url:http://localhost:18080}") String baseUrl) {
        var strategies = ExchangeStrategies.builder()
                .codecs(c -> c.defaultCodecs().maxInMemorySize(4 * 1024 * 1024))
                .build();
        return WebClient.builder()
                .baseUrl(baseUrl)     // 👈 important
                .exchangeStrategies(strategies)
                .build();
    }
}
