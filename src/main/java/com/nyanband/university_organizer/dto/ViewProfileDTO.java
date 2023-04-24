package com.nyanband.university_organizer.dto;

import com.nyanband.university_organizer.entity.enums.Gender;
import lombok.Value;

import java.util.List;

@Value
public class ViewProfileDTO {
    Long id;
    String name;
    Integer age;
    Gender gender;
    String git;
    String info;
    List<ViewTechnologyDTO> technologies;
}
