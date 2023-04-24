package com.nyanband.university_organizer.dto;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class UpdateProjectDTO {
    Long id;
    String name;
    String description;
    List<Long> technologies;
}
