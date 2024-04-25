package com.yevhenii.organisationSystem.services;


import com.yevhenii.organisationSystem.entity.ApplicationToGetVenue;
import com.yevhenii.organisationSystem.entity.Edge;
import com.yevhenii.organisationSystem.entity.Venue;
import com.yevhenii.organisationSystem.payload.response.AlgorithmResponse;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface KunAlgorithmService {
    List<Edge> getApplicationForVenues(Timestamp date, long userId);
    Map<Venue, ApplicationToGetVenue> doKuhnAlgorithm(List<Edge> edgeList);
    void saveMatching(List<Edge> edgeList);
    List<AlgorithmResponse> getMatchingEdges(long userId,Timestamp date);
    List<AlgorithmResponse> getKuhnResult(Map<Venue, ApplicationToGetVenue> map);

}
