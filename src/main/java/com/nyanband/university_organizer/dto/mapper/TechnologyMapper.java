package com.nyanband.university_organizer.dto.mapper;

import com.nyanband.university_organizer.dto.TechnologyDTO;
import com.nyanband.university_organizer.entity.Technology;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {EntityMapper.class})
public interface TechnologyMapper {

    TechnologyMapper INSTANCE = Mappers.getMapper(TechnologyMapper.class);

    TechnologyDTO toDto(Technology technology);

    Technology toEntity(TechnologyDTO technologyDTO);

}
