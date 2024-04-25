package com.yevhenii.ticketingService.repository;

import com.yevhenii.ticketingService.entity.Edge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface EdgeRepository extends JpaRepository<Edge,Long> {

    @Query("select  e  from Edge e where e.date = :date and e.venue.user.id =:userId")
    List<Edge>  getApplicationForVenues(Timestamp date, long userId);
    @Query("select  e  from Edge e where e.date = :date and e.venue.user.id =:userId and e.isMatching = true")
    List<Edge>  getMatchingEdges(long userId,Timestamp date);

    //void updateEdge(Edge edge)

}
