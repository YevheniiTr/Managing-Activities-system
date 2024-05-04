package com.yevhenii.organisationSystem.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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



    public String  getStartTime(){
        LocalDateTime localDateTime = startDate.toLocalDateTime();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return localDateTime.toLocalTime().format(timeFormatter);
    }

    public String  getOnlyStartDate(){
        LocalDateTime localDateTime = startDate.toLocalDateTime();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDateTime.toLocalDate().format(dateFormatter);
    }


}
