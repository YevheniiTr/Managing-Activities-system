package com.yevhenii.organisationSystem.services;

import com.yevhenii.organisationSystem.entity.PlannedActivities;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlannedActivitiesService {
    List<PlannedActivities> getPlannedActivities(Long venueId);

    List<PlannedActivities> findAllForToday();
}
