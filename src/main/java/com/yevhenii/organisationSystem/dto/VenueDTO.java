package com.yevhenii.organisationSystem.dto;



import lombok.Value;


@Value
public class VenueDTO {
    private Long venueId;
    private String title;
    private int maximumSeats;
    private String description;
    private String adresIndex;
    private int rentPrice;
    private String streetName;
    private String cityName;
}
