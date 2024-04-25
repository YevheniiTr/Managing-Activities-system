package com.yevhenii.ticketingService.controller;

import com.yevhenii.ticketingService.controller.util.SecurityUtils;
import com.yevhenii.ticketingService.dto.MatchingPairDTO;
import com.yevhenii.ticketingService.dto.MatchingResultDTO;
import com.yevhenii.ticketingService.entity.ApplicationToGetVenue;
import com.yevhenii.ticketingService.entity.Edge;
import com.yevhenii.ticketingService.entity.Venue;
import com.yevhenii.ticketingService.service.KunAlgorithmService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class EdgeAlgoController {

    KunAlgorithmService kunAlgorithmService;
    SecurityUtils securityUtils;

    public EdgeAlgoController(KunAlgorithmService kunAlgorithmService, SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
        this.kunAlgorithmService = kunAlgorithmService;

    }

    @GetMapping(value = "/test")
    public MatchingResultDTO manageVenues(Model model) {
        int userID = 25;
        String date = "2024-03-06 12:00:00";
        Timestamp timestamp = Timestamp.valueOf(date);
        System.out.println(timestamp);
        List<Edge> edgeList = kunAlgorithmService.getApplicationForVenues(timestamp, securityUtils.getUserId());
        model.addAttribute("edges", kunAlgorithmService.getApplicationForVenues(timestamp, securityUtils.getUserId()));
        //return "not_admin_error";
        Map<Venue,ApplicationToGetVenue> result = kunAlgorithmService.doKuhnAlgorithm(edgeList);
        List<MatchingPairDTO> matchingPairs = result.entrySet().stream()
                .map(entry -> new MatchingPairDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return new MatchingResultDTO(matchingPairs);
    }


    @GetMapping("/matching")
    public List<Edge> getMatchingEdges(@RequestParam Timestamp date){
        return kunAlgorithmService.getMatchingEdges(securityUtils.getUserId(),date);
    }
}
