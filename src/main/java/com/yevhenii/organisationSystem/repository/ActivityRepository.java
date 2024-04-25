package com.yevhenii.organisationSystem.repository;

import com.yevhenii.organisationSystem.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {
    @Query("select count(a) > 0 from Activity  a where a.id  =:activityId and a.user.id =:userId")
    boolean isActivityBelongToUser(Long userId,Long activityId);
    void deleteById(Long activityId);
    @Query("select a from Activity  a where a.title = :activityTitle and a.user.id =:userId")
    Activity findByActivityTitle(String activityTitle,Long userId);
    @Query("select count(a) > 0 from Activity  a where a.title  =:activityTitle and a.user.id =:userId")
    boolean isActivityBelongToUserByTitle(Long userId, String activityTitle);
}
