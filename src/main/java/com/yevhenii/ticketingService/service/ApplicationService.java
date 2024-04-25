package com.yevhenii.ticketingService.service;


import com.yevhenii.ticketingService.dto.ApplicationDTO;
import com.yevhenii.ticketingService.dto.SaveApplicationDto;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {

     void save(SaveApplicationDto saveApplicationDto);

     List<SaveApplicationDto> getApplicationByUserId(long userId);

     List<ApplicationDTO> getApplicationByUserId2(long userId);

     List<SaveApplicationDto> findAll();

     List<ApplicationDTO> findAll2();

     //Optional<SaveApplicationDto> find(long userId);

     Optional<SaveApplicationDto> find(long id);
     Optional<ApplicationDTO> find2(long id);


    void decline(long id);

    void approve(long id);

}
