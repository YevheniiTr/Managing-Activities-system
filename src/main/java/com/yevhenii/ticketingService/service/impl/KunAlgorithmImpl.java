package com.yevhenii.ticketingService.service.impl;

import com.yevhenii.ticketingService.entity.*;
import com.yevhenii.ticketingService.entity.enums.EApplicationStatus;
import com.yevhenii.ticketingService.repository.EdgeRepository;
import com.yevhenii.ticketingService.service.KunAlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class KunAlgorithmImpl implements KunAlgorithmService {
    EdgeRepository edgeRepository;
    Graph graph;

    @Autowired
    public KunAlgorithmImpl(EdgeRepository edgeRepository) {
        this.edgeRepository = edgeRepository;
    }

    @Override
    public List<Edge> getApplicationForVenues(Timestamp date, long userId) {
        return edgeRepository.getApplicationForVenues(date, userId);
    }


    @Override
    public Map<Venue, ApplicationToGetVenue> doKuhnAlgorithm(List<Edge> edgeList) {
        graph = new Graph(edgeList);
        graph.maxMatching();
        graph.createMap();
        graph.changeStatus();
        saveMatching(edgeList);
                graph.displayAdjMatrix();
        int[] resultMatching = graph.maxMatching();
        System.out.println(Arrays.toString(resultMatching));
        return graph.getResultMatching();
    }

    @Override
    public void saveMatching(List<Edge> edgeList) {
        for (Edge edge : edgeList){
            if (edge.isMatching())  {
                edgeRepository.save(edge);
            }
        }
    }

    @Override
    public List<Edge> getMatchingEdges(long userId, Timestamp date) {
        return edgeRepository.getMatchingEdges(userId,date);
    }




}
