package com.nyanband.university_organizer.dto;

import com.nyanband.university_organizer.entity.enums.Gender;
import lombok.Value;

import java.util.List;

@Value
public class SaveProfileDTO {
    String name;
    Integer age;
    Gender gender;
    String git;
    String info;
    Long userId;
    List<Long> technologies;
}
