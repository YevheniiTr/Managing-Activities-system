package com.yevhenii.organisationSystem.dto;



import lombok.Value;


@Value
public class VenueDTO {
     Long venueId;
     String title;
     int maximumSeats;
     String description;
     String adresIndex;
     int rentPrice;
     String streetName;
     String cityName;
}
