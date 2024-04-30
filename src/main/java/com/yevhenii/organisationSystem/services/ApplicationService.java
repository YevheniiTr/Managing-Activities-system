package com.yevhenii.organisationSystem.services;

import com.yevhenii.organisationSystem.dto.SaveApplicationDTO;
import com.yevhenii.organisationSystem.dto.VenueDTO;
import com.yevhenii.organisationSystem.entity.Venue;
import com.yevhenii.organisationSystem.payload.request.SendApplicationRequest;

import java.util.List;

public interface ApplicationService {
    void save(SaveApplicationDTO saveApplication);
    boolean isApplicationBelongToUser(Long userId,Long applicationId);
    void delete(Long activityId);
    void sendApplication(SaveApplicationDTO saveApplicationDTO, List<VenueDTO> venueTitleList);
    void sendApplication2(SaveApplicationDTO saveApplicationDTO, List<Long> venueIds);
    void approve(Long applicationId);
    void decline(Long applicationId);

}
