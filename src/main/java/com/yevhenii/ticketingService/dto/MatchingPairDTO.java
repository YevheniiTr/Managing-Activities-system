package com.yevhenii.ticketingService.dto;

import com.yevhenii.ticketingService.entity.ApplicationToGetVenue;
import com.yevhenii.ticketingService.entity.Venue;

public class MatchingPairDTO {
    private Venue venue;
    private ApplicationToGetVenue application;

    public MatchingPairDTO(Venue venue, ApplicationToGetVenue application) {
        this.venue = venue;
        this.application = application;
    }
}
