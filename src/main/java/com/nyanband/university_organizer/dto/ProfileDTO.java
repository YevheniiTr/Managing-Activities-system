package com.nyanband.university_organizer.dto;

import com.nyanband.university_organizer.entity.BaseEntity;
import com.nyanband.university_organizer.entity.Technology;
import com.nyanband.university_organizer.entity.User;
import com.nyanband.university_organizer.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Value
public class ProfileDTO {
    Long id;
    String name;
    Integer age;
    Gender gender;
    String git;
    String info;
    List<String> technologies;
}
