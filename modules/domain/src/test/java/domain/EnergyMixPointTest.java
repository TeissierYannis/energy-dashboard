package domain;

import fr.teissiery.energy.domain.model.EnergyMixPoint;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnergyMixPointTest {
    @Test
    void shouldCreateValidPoint() {
        var p = new EnergyMixPoint(
                Instant.now(),
                new BigDecimal("50000"),
                new BigDecimal("2000"),
                new BigDecimal("30000"),
                new BigDecimal("5000"),
                new BigDecimal("7000"),
                new BigDecimal("6000"),
                new BigDecimal("500"),
                new BigDecimal("-1000"), // export net possible
                new BigDecimal("45.5")
        );
        assertNotNull(p);
    }

    @Test
    void shouldRejectNegativeLoad() {
        assertThrows(IllegalArgumentException.class, () ->
                new EnergyMixPoint(
                        Instant.now(),
                        new BigDecimal("-1"),
                        null,null,null,null,null,null,
                        null,
                        null
                )
        );
    }
}
