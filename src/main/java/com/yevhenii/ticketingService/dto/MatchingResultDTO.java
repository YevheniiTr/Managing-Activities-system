package com.yevhenii.ticketingService.dto;

import java.util.List;

public class MatchingResultDTO {
    private List<MatchingPairDTO> matchingPairs;

    public MatchingResultDTO(List<MatchingPairDTO> matchingPairs) {
        this.matchingPairs = matchingPairs;
    }

}
