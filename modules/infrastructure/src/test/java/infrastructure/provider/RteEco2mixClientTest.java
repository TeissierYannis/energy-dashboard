package infrastructure.provider;

import com.github.tomakehurst.wiremock.WireMockServer;
import fr.teissiery.energy.infrastructure.provider.RteEco2mixClient;
import org.junit.jupiter.api.*;
import org.springframework.web.reactive.function.client.WebClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

class RteEco2mixClientTest {

    static WireMockServer wm;

    @BeforeAll
    static void start() {
        wm = new WireMockServer(18080);
        wm.start();
        configureFor("localhost", 18080);
    }

    @AfterAll
    static void stop() {
        wm.stop();
    }

    @Test
    void fetchCurrent_ok() {
        wm.stubFor(get(urlEqualTo("/mix/current"))
                .willReturn(okJson("""
                  {"ts":"2024-01-01T12:00:00Z","loadMw":50000,"carbonIntensity":45.2}
                """)));

        var client = new RteEco2mixClient(WebClient.builder().baseUrl("http://localhost:18080").build());
        var cur = client.fetchCurrent();
        assertEquals("2024-01-01T12:00:00Z", cur.ts().toString());
    }

    @Test
    void fetchHistory_ok() {
        wm.stubFor(get(urlPathEqualTo("/mix/history"))
                .withQueryParam("from", matching(".*"))
                .withQueryParam("to", matching(".*"))
                .withQueryParam("step", matching(".*"))
                .willReturn(okJson("""
                  {"data":[
                    {"ts":"2024-01-01T00:00:00Z","loadMw":45000},
                    {"ts":"2024-01-01T00:15:00Z","loadMw":45500}
                  ]}
                """)));

        var client = new RteEco2mixClient(
                WebClient.builder().baseUrl("http://localhost:18080").build()
        );
        var list = client.fetchHistory(java.time.Instant.parse("2024-01-01T00:00:00Z"),
                java.time.Instant.parse("2024-01-01T01:00:00Z"),
                fr.teissiery.energy.domain.model.TimeStep.M15);
        assertEquals(2, list.size());
    }
}