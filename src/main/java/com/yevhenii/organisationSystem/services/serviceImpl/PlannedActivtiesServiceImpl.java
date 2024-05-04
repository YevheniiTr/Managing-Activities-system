package com.yevhenii.organisationSystem.services.serviceImpl;

import com.yevhenii.organisationSystem.entity.PlannedActivities;
import com.yevhenii.organisationSystem.repository.PlannedActivityRepository;
import com.yevhenii.organisationSystem.services.PlannedActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlannedActivtiesServiceImpl implements PlannedActivitiesService {
    PlannedActivityRepository plannedActivityRepository;

    @Autowired
    public PlannedActivtiesServiceImpl(PlannedActivityRepository plannedActivityRepository) {
        this.plannedActivityRepository = plannedActivityRepository;
    }

    @Override
    public List<PlannedActivities> getPlannedActivities(Long venueId) {
        return plannedActivityRepository.getPlannedActivities(venueId);
    }
}
