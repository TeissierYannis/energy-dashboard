create table energy_mix_point
(
    id      bigserial primary key,
    ts      timestamptz    not null,
    load_mw numeric(12, 2) not null
);
