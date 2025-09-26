package application.usecase;

import fr.teissiery.energy.application.usecase.GetCurrentMixUseCase;
import fr.teissiery.energy.domain.model.EnergyMixSnapshot;
import fr.teissiery.energy.domain.ports.MixProviderPort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GetCurrentMixUseCaseTest {

    @Test
    void returnsSnapshotFromProvider() {
        var provider = Mockito.mock(MixProviderPort.class);
        var expected = new EnergyMixSnapshot(
                Instant.parse("2024-01-01T12:00:00Z"),
                new BigDecimal("50000"),
                null, null, null, null, null, null,
                new BigDecimal("1000"),
                new BigDecimal("45")
        );
        when(provider.fetchCurrent()).thenReturn(expected);

        var uc = new GetCurrentMixUseCase(provider);
        var actual = uc.execute();

        assertEquals(expected, actual);
    }
}