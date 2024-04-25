package com.yevhenii.ticketingService.service;

import com.yevhenii.ticketingService.entity.ApplicationToGetVenue;
import com.yevhenii.ticketingService.entity.Edge;
import com.yevhenii.ticketingService.entity.Venue;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface KunAlgorithmService {
    List<Edge> getApplicationForVenues(Timestamp date, long userId);
    Map<Venue, ApplicationToGetVenue> doKuhnAlgorithm(List<Edge> edgeList);
    void saveMatching(List<Edge> edgeList);
    List<Edge> getMatchingEdges(long userId,Timestamp date);
}
