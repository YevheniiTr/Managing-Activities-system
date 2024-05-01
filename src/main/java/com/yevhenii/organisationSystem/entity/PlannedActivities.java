package com.yevhenii.organisationSystem.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Plannedactivities")
public class PlannedActivities extends BaseEntity{
    @Column(name = "startdate")
    private Timestamp startDate;
    @Column(name = "enddate")
    private Timestamp endDate;
    @Column(name = "status")
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activityid")
    Activity activity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venueid")
    Venue venue;


}
