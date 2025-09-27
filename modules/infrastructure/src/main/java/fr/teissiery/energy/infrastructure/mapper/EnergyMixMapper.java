package fr.teissiery.energy.infrastructure.mapper;

import fr.teissiery.energy.domain.model.EnergyMixPoint;
import fr.teissiery.energy.infrastructure.jpa.EnergyMixPointEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnergyMixMapper {
    EnergyMixPointEntity toEntity(EnergyMixPoint p);
    EnergyMixPoint toDomain(EnergyMixPointEntity e);
}
