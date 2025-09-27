package fr.teissiery.energy.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface EnergyMixPointJpaRepository extends JpaRepository<EnergyMixPointEntity, Long> {

    @Query("select e from EnergyMixPointEntity e where e.ts >= :from and e.ts < :to order by e.ts")
    List<EnergyMixPointEntity> findRange(Instant from, Instant to);
}
