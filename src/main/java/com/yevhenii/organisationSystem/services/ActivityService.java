package com.yevhenii.organisationSystem.services;

import com.yevhenii.organisationSystem.dto.SaveActivityDTO;

public interface ActivityService {
    void save(SaveActivityDTO saveActivityDTO);
    boolean isActivityBelongToUser(Long userId,Long activityId);
    void delete(Long activityId);
    boolean isActivityBelongToUserByTitle(Long userId,String activityTitle);
}
