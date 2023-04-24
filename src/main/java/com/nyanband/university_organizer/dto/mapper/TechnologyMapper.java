package com.nyanband.university_organizer.dto.mapper;

import com.nyanband.university_organizer.dto.ViewTechnologyDTO;
import com.nyanband.university_organizer.entity.Technology;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {EntityMapper.class})
public interface TechnologyMapper {

    TechnologyMapper INSTANCE = Mappers.getMapper(TechnologyMapper.class);

    ViewTechnologyDTO toDto(Technology technology);

    Technology toEntity(ViewTechnologyDTO technologyDTO);

}
