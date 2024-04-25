package com.yevhenii.organisationSystem.services.serviceImpl;

import com.yevhenii.organisationSystem.dto.mapper.ApplicationMapper;
import com.yevhenii.organisationSystem.entity.*;
import com.yevhenii.organisationSystem.payload.response.AlgorithmResponse;
import com.yevhenii.organisationSystem.repository.EdgeRepository;
import com.yevhenii.organisationSystem.services.KunAlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class KunAlgorithmImpl implements KunAlgorithmService {
    EdgeRepository edgeRepository;
    Graph graph;
    ApplicationMapper applicationMapper;

    @Autowired
    public KunAlgorithmImpl(EdgeRepository edgeRepository, ApplicationMapper applicationMapper) {
        this.edgeRepository = edgeRepository;
        this.applicationMapper = applicationMapper;

    }

    @Override
    public List<Edge> getApplicationForVenues(Timestamp date, long userId) {
        return edgeRepository.getApplicationForVenues(date, userId);
    }


    @Override
    public Map<Venue, ApplicationToGetVenue> doKuhnAlgorithm(List<Edge> edgeList) {
        graph = new Graph(edgeList);
        graph.maximumBipartiteMatching();
        graph.createMap();
        graph.changeStatus();
        saveMatching(edgeList);
        graph.displayAdjMatrix();
        int[] resultMatching = graph.maximumBipartiteMatching();
        System.out.println(Arrays.toString(resultMatching));
        return graph.getResultMatching();
    }

    @Override
    public void saveMatching(List<Edge> edgeList) {
        for (Edge edge : edgeList) {
            if (edge.isMatching()) {
                edgeRepository.save(edge);
            }
        }
    }

    @Override
    public List<AlgorithmResponse> getMatchingEdges(long userId, Timestamp date) {
        List<Edge> edgeList = edgeRepository.getMatchingEdges(userId, date);
        List<AlgorithmResponse> responseList = new ArrayList<>();
        for(Edge edge : edgeList){
            Venue venue = edge.getVenue();
            Activity activity = edge.getApplicationToGetVenue().getActivity();
            responseList.add(mapReponse(venue,activity));
        }
        return responseList;
    }

    private AlgorithmResponse mapReponse(Venue venue,Activity activity){
        AlgorithmResponse algorithmResponse = new AlgorithmResponse();
        algorithmResponse.setVenueTitle(venue.getTitle());
        algorithmResponse.setActivityTitle(activity.getTitle());
        algorithmResponse.setGenre(activity.getGenre());
        algorithmResponse.setDescription(activity.getDescription());
        algorithmResponse.setOrganisation(activity.getOrganisation());
        algorithmResponse.setAmountSeats(activity.getAmountSeats());
        algorithmResponse.setActivityType(activity.getActivityType());
        return algorithmResponse;

    }

    @Override
    public List<AlgorithmResponse> getKuhnResult(Map<Venue, ApplicationToGetVenue> map) {
        List<AlgorithmResponse> responseList = new ArrayList<>();
        for (Map.Entry<Venue, ApplicationToGetVenue> entry : map.entrySet()) {
            Venue venue = entry.getKey();
            Activity activity = entry.getValue().getActivity();
            responseList.add(mapReponse(venue,activity));
        }
        return responseList;
    }


}
