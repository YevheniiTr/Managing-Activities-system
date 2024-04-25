package com.yevhenii.ticketingService.repository;


import com.yevhenii.ticketingService.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {
    @Query("select a  from Application a  where a.user.id = :userId")
    List<Application> getApplicationByUserId(long userId);
    @Query("select a  from Application a  where a.id = :applicationId")
    Application findById2(long applicationId);


}
