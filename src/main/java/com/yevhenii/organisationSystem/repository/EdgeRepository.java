package com.yevhenii.organisationSystem.repository;


import com.yevhenii.organisationSystem.entity.ApplicationToGetVenue;
import com.yevhenii.organisationSystem.entity.Edge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EdgeRepository extends JpaRepository<Edge,Long> {

    @Query("select  e  from Edge e where e.date = :date and e.venue.user.id =:userId")
    List<Edge>  getApplicationForVenues(Timestamp date, long userId);
    @Query("select  e  from Edge e where e.date = :date and e.venue.user.id =:userId and e.isMatching = true")
    List<Edge>  getMatchingEdges(long userId,Timestamp date);

    @Query("select  e from Edge e where e.id =:id")
    Optional<Edge> findById(Long id);

    @Query("select e from Edge e where e.applicationToGetVenue.id = :applicationId")
    List<Edge> findByApplicationId(Long applicationId);
    @Transactional
    @Query("SELECT e FROM Edge e JOIN e.venue v WHERE v.user.id = :userId AND DATE(e.date) = :startDate")
    List<Edge> findUserEdgesForDate(
            @Param("startDate") LocalDate startDate,
            @Param("userId") Long userId
    );
    @Query("select e FROM Edge e where e.applicationToGetVenue.activity.user.id =:userId AND DATE(e.date) =:startDate ")
    List<Edge> findEdgesByOrganisatorAndDate(
            @Param("startDate") LocalDate startDate,
            @Param("userId") Long userId
    );


    @Query("select e from Edge e where e.applicationToGetVenue.activity.user.id = :userId")
    List<Edge> findOrganisatorApplicationsByUserId(long userId);

}
