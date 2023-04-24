package com.nyanband.university_organizer.dto;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class ViewProjectDTO {
    Long id;
    String name;
    String description;
    LocalDateTime createdAt;
    ProfileDTO leader;
    List<ProfileDTO> members;
    List<ProfileDTO> candidates;
    List<ViewTechnologyDTO> technologies;

    @Value
    public static class ProfileDTO {
        Long id;
        String name;
    }
}
