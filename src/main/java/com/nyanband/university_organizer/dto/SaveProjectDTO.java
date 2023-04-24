package com.nyanband.university_organizer.dto;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class SaveProjectDTO {
    String name;
    String description;
    LocalDateTime createdAt;
    Long leaderId;
    List<Long> technologies;
}
