package application.usecase;

import fr.teissiery.energy.application.usecase.GetMixHistoryUseCase;
import fr.teissiery.energy.domain.model.EnergyMixPoint;
import fr.teissiery.energy.domain.model.TimeStep;
import fr.teissiery.energy.domain.ports.MixProviderPort;
import fr.teissiery.energy.domain.ports.MixRepositoryPort;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetMixHistoryUseCaseTest {

    @Test
    void fetchesAndPersistsHistory() {
        var provider = Mockito.mock(MixProviderPort.class);
        var repo = Mockito.mock(MixRepositoryPort.class);

        var from = Instant.parse("2024-01-01T00:00:00Z");
        var to   = Instant.parse("2024-01-01T01:00:00Z");

        var pts = List.of(
                new EnergyMixPoint(from, new BigDecimal("45000"), null,null,null,null,null,null, null, null),
                new EnergyMixPoint(from.plusSeconds(900), new BigDecimal("45500"), null,null,null,null,null,null, null, null)
        );
        when(provider.fetchHistory(from, to, TimeStep.M15)).thenReturn(pts);

        var uc = new GetMixHistoryUseCase(provider, repo);
        var result = uc.execute(from, to, TimeStep.M15);

        assertEquals(2, result.size());

        var captor = ArgumentCaptor.forClass(List.class);
        verify(repo, times(1)).saveAll(captor.capture());
        assertEquals(2, ((List<?>)captor.getValue()).size());
    }

    @Test
    void validatesFromBeforeTo() {
        var provider = Mockito.mock(MixProviderPort.class);
        var repo = Mockito.mock(MixRepositoryPort.class);
        var uc = new GetMixHistoryUseCase(provider, repo);

        var from = Instant.parse("2024-01-01T00:00:00Z");
        var to   = Instant.parse("2024-01-01T00:00:00Z");
        assertThrows(IllegalArgumentException.class, () -> uc.execute(from, to, TimeStep.M15));
    }
}