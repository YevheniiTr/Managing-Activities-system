package com.yevhenii.organisationSystem.repository;

import com.yevhenii.organisationSystem.entity.PlannedActivities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlannedActivityRepository extends JpaRepository<PlannedActivities,Long> {

    @Query("select p from PlannedActivities p where p.activity.id = :activityId")
    Optional<PlannedActivities> findByActivityId(Long activityId);


}
