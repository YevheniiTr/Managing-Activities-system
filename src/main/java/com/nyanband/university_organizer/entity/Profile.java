package com.nyanband.university_organizer.entity;

import com.nyanband.university_organizer.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "profile")
public class Profile extends BaseEntity {

    String name;

    Integer age;

    @Enumerated(EnumType.ORDINAL)
    Gender gender;

    @Column(name = "git_link")
    String git;

    String info;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "profile_lang",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "lang_id")
    )
    List<Technology> technologies;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    public Profile(Long id) {
        super(id);
    }
}
