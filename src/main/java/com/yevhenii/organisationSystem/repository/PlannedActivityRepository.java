package com.yevhenii.organisationSystem.repository;

import com.yevhenii.organisationSystem.entity.PlannedActivities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlannedActivityRepository extends JpaRepository<PlannedActivities,Long> {

    @Query("select p from PlannedActivities p where p.activity.id = :activityId")
    Optional<PlannedActivities> findByActivityId(Long activityId);

    @Query("SELECT p FROM PlannedActivities p WHERE p.venue.id = :venueId AND DATE(p.startDate) >= CURRENT_DATE")
    List<PlannedActivities> getPlannedActivities(Long venueId);
}
