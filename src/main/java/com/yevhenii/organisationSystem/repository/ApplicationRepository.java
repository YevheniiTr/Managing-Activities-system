package com.yevhenii.organisationSystem.repository;

import com.yevhenii.organisationSystem.entity.ApplicationToGetVenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationToGetVenue,Long> {
    @Query("select a  from ApplicationToGetVenue a  where a.activity.user.id = :userId")
    List<ApplicationToGetVenue> getApplicationByUserId(long userId);
    void deleteById(Long applicationId);
    @Query("select count(a) > 0 from ApplicationToGetVenue a where a.id = :applicationId and a.activity.user.id = :userId")
    boolean isApplicationBelongToUser(Long userId, Long applicationId);
}
