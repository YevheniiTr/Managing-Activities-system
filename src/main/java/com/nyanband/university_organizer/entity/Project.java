package com.nyanband.university_organizer.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Project")
public class Project extends BaseEntity {

    String name;

    @ManyToOne
    @JoinColumn(name = "leader_id")
    Profile leader;

    @Column(name = "description")
    String description;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Project_Member",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    List<Profile> members;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Project_Candidate",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    List<Profile> candidates;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Project_Technology",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "lang_id")
    )
    List<Technology> technologies;


    public Project(Long id) {
        super(id);
    }
}
