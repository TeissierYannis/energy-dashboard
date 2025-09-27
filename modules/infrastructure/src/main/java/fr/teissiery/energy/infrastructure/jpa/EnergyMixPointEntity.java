package fr.teissiery.energy.infrastructure.jpa;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "energy_mix_point")
public class EnergyMixPointEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ts", nullable = false)
    private Instant ts;

    @Column(name = "load_mw", nullable = false, precision = 12, scale = 2)
    private BigDecimal loadMw;

    @Column(name = "fossil_mw", precision = 12, scale = 2)
    private BigDecimal fossilMw;

    @Column(name = "nuclear_mw", precision = 12, scale = 2)
    private BigDecimal nuclearMw;

    @Column(name = "hydro_mw", precision = 12, scale = 2)
    private BigDecimal hydroMw;

    @Column(name = "wind_mw", precision = 12, scale = 2)
    private BigDecimal windMw;

    @Column(name = "solar_mw", precision = 12, scale = 2)
    private BigDecimal solarMw;

    @Column(name = "bio_mw", precision = 12, scale = 2)
    private BigDecimal bioMw;

    @Column(name = "net_import_mw", precision = 12, scale = 2)
    private BigDecimal netImportMw;

    @Column(name = "carbon_intensity_gco2_kwh", precision = 8, scale = 2)
    private BigDecimal carbonIntensityGCo2PerKwh;

    // getters/setters no-args constructor
    public EnergyMixPointEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTs() {
        return ts;
    }

    public void setTs(Instant ts) {
        this.ts = ts;
    }

    public BigDecimal getLoadMw() {
        return loadMw;
    }

    public void setLoadMw(BigDecimal loadMw) {
        this.loadMw = loadMw;
    }

    public BigDecimal getFossilMw() {
        return fossilMw;
    }

    public void setFossilMw(BigDecimal fossilMw) {
        this.fossilMw = fossilMw;
    }

    public BigDecimal getNuclearMw() {
        return nuclearMw;
    }

    public void setNuclearMw(BigDecimal nuclearMw) {
        this.nuclearMw = nuclearMw;
    }

    public BigDecimal getHydroMw() {
        return hydroMw;
    }

    public void setHydroMw(BigDecimal hydroMw) {
        this.hydroMw = hydroMw;
    }

    public BigDecimal getWindMw() {
        return windMw;
    }

    public void setWindMw(BigDecimal windMw) {
        this.windMw = windMw;
    }

    public BigDecimal getSolarMw() {
        return solarMw;
    }

    public void setSolarMw(BigDecimal solarMw) {
        this.solarMw = solarMw;
    }

    public BigDecimal getBioMw() {
        return bioMw;
    }

    public void setBioMw(BigDecimal bioMw) {
        this.bioMw = bioMw;
    }

    public BigDecimal getNetImportMw() {
        return netImportMw;
    }

    public void setNetImportMw(BigDecimal netImportMw) {
        this.netImportMw = netImportMw;
    }

    public BigDecimal getCarbonIntensityGCo2PerKwh() {
        return carbonIntensityGCo2PerKwh;
    }

    public void setCarbonIntensityGCo2PerKwh(BigDecimal carbonIntensityGCo2PerKwh) {
        this.carbonIntensityGCo2PerKwh = carbonIntensityGCo2PerKwh;
    }
}
