package com.nyanband.university_organizer.dto.mapper;

import com.nyanband.university_organizer.dto.SaveProjectDTO;
import com.nyanband.university_organizer.dto.ViewProjectDTO;
import com.nyanband.university_organizer.entity.Profile;
import com.nyanband.university_organizer.entity.Project;
import com.nyanband.university_organizer.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {EntityMapper.class, TechnologyMapper.class})
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    ViewProjectDTO toDto(Project project);

    ViewProjectDTO.ProfileDTO toDto(Profile profile);

    @Mapping(source = "leaderId", target = "leader")
    Project toEntity(SaveProjectDTO projectDTO);

}
