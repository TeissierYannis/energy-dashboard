package fr.teissiery.energy.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "fr.teissiery.energy")
@EnableCaching
public class EnergyDashboardApplication {
    public static void main(String[] args) {
        SpringApplication.run(EnergyDashboardApplication.class, args);
    }
}
