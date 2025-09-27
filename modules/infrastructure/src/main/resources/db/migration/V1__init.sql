create table if not exists energy_mix_point (
    id bigserial primary key,
    ts timestamptz not null,
    load_mw numeric(12,2) not null,
    fossil_mw numeric(12,2),
    nuclear_mw numeric(12,2),
    hydro_mw numeric(12,2),
    wind_mw numeric(12,2),
    solar_mw numeric(12,2),
    bio_mw numeric(12,2),
    net_import_mw numeric(12,2),
    carbon_intensity_gco2_kwh numeric(8,2)
    );

create index if not exists idx_energy_mix_point_ts on energy_mix_point(ts);
