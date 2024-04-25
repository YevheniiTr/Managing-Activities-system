package com.yevhenii.organisationSystem.dto.mapper;


import com.yevhenii.organisationSystem.dto.*;
import com.yevhenii.organisationSystem.entity.Activity;
import com.yevhenii.organisationSystem.entity.Venue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {EntityMapper.class})
public interface ApplicationMapper {
    ApplicationMapper INSTANCE = Mappers.getMapper(ApplicationMapper.class);

    ActivityDTO activityToDto(Activity activity);
    Activity SaveActivityDTOtoEntity(SaveActivityDTO saveActivityDTO);
    @Mapping(source = "street.streetName", target = "streetName")
    @Mapping(source = "street.city.cityName", target ="cityName")
    SaveVenueDTO venueToSaveDTO(Venue venue);
    @Mapping(source = "street.streetName", target = "streetName")
    @Mapping(source = "street.city.cityName", target ="cityName")
    @Mapping(source = "id", target = "venueId")
    VenueDTO venueToDTO(Venue venue);


}
