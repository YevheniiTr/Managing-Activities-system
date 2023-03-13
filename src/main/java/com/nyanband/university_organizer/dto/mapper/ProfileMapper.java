package com.nyanband.university_organizer.dto.mapper;

import com.nyanband.university_organizer.dto.ProfileDTO;
import com.nyanband.university_organizer.dto.SaveProfileDTO;
import com.nyanband.university_organizer.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {EntityMapper.class})
public interface ProfileMapper {

    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    ProfileDTO toDto(Profile profile);

    Profile toEntity(SaveProfileDTO profileDTO);

}
