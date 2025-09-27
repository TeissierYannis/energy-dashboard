package infrastructure.jpa;

import fr.teissiery.energy.domain.model.EnergyMixPoint;
import fr.teissiery.energy.domain.model.TimeStep;
import fr.teissiery.energy.infrastructure.jpa.EnergyMixPointEntity;
import fr.teissiery.energy.infrastructure.jpa.EnergyMixPointJpaRepository;
import fr.teissiery.energy.infrastructure.jpa.MixRepositoryAdapter;
import fr.teissiery.energy.infrastructure.mapper.EnergyMixMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MixRepositoryAdapterIT {

    @Container
    static PostgreSQLContainer<?> pg = new PostgreSQLContainer<>("postgres:16");

    @DynamicPropertySource
    static void dbProps(DynamicPropertyRegistry reg) {
        reg.add("spring.datasource.url", pg::getJdbcUrl);
        reg.add("spring.datasource.username", pg::getUsername);
        reg.add("spring.datasource.password", pg::getPassword);
        reg.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
        reg.add("spring.flyway.enabled", () -> "true");
    }

    @Autowired
    MixRepositoryAdapter adapter;

    @Test
    void saveAndLoadRange() {
        var t0 = Instant.parse("2024-01-01T00:00:00Z");
        List<EnergyMixPoint> pts = List.of(
                new EnergyMixPoint(t0, new BigDecimal("45000"), null,null,null,null,null,null, null, null),
                new EnergyMixPoint(t0.plusSeconds(900), new BigDecimal("45500"), null,null,null,null,null,null, null, null)
        );
        adapter.saveAll(pts);

        var loaded = adapter.load(t0, t0.plusSeconds(1800), TimeStep.M15);
        assertEquals(2, loaded.size());
        assertEquals(new BigDecimal("45000"), loaded.get(0).loadMw());
    }
}

/** Contexte minimal pour les tests JPA */
@EnableAutoConfiguration
@EntityScan(basePackageClasses = EnergyMixPointEntity.class)
@ComponentScan(basePackageClasses = {MixRepositoryAdapter.class, EnergyMixPointJpaRepository.class, EnergyMixMapper.class})
class TestConfig {
    // MapStruct via factory si besoin, mais componentModel=spring suffit avec @ComponentScan.
}