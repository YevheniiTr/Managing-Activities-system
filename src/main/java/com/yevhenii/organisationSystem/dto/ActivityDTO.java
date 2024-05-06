package com.yevhenii.organisationSystem.dto;


import com.yevhenii.organisationSystem.entity.ActivityBanner;
import lombok.Data;

@Data
public class ActivityDTO {
    private Long id;
    private String title;
    private String description;
    private int amountSeats;
    private String organisation;
    private String genre;
    private String activityType;
    private Long userId;

}
