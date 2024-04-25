package com.yevhenii.ticketingService.dto.mapper;

import com.yevhenii.ticketingService.dto.ApplicationDTO;
import com.yevhenii.ticketingService.dto.SaveApplicationDto;
import com.yevhenii.ticketingService.entity.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {EntityMapper.class, FileMapper.class})
public interface ApplicationMapper {
    ApplicationMapper INSTANCE = Mappers.getMapper(ApplicationMapper.class);

    @Mapping(source ="userId",target =  "user")
    @Mapping(source ="fileId",target =  "proveFile")
    Application  toEntity(SaveApplicationDto saveApplicationDto);

    @Mapping(source ="fileId",target =  "proveFile")
    Application toEntity(ApplicationDTO applicationDTO);

    @Mapping(source = "proveFile", target = "fileId")
    SaveApplicationDto toDto(Application application);
    @Mapping(source = "proveFile", target = "fileId")
    ApplicationDTO toDto2(Application application);

}
