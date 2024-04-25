package com.yevhenii.organisationSystem.services;

import com.yevhenii.organisationSystem.dto.SaveApplicationDTO;
import com.yevhenii.organisationSystem.payload.request.SendApplicationRequest;

public interface ApplicationService {
    void save(SaveApplicationDTO saveApplication);
    boolean isApplicationBelongToUser(Long userId,Long applicationId);
    void delete(Long activityId);
    void sendApplication(SaveApplicationDTO saveApplicationDTO, SendApplicationRequest sendApplicationRequest);
    void approve();
    void decline();

}
