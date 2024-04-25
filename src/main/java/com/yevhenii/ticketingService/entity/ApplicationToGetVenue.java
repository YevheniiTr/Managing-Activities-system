package com.yevhenii.ticketingService.entity;

import com.yevhenii.ticketingService.entity.enums.EApplicationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "applicationtogetvenue")
public class ApplicationToGetVenue extends BaseEntity {


    @Enumerated(EnumType.STRING)
    private EApplicationStatus status;
    @Column(name = "startdate")
    private Timestamp startDate;
    @Column(name = "enddate")
    private Timestamp endDate;
    @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "activityID")
    Activity activity;
    @OneToMany(mappedBy = "applicationToGetVenue",fetch = FetchType.LAZY)
    List<Edge> edgeList;




    public ApplicationToGetVenue(Long id){
        super(id);
    }

    public ApplicationToGetVenue(EApplicationStatus status, Timestamp startDate, Timestamp endDate) {
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "ApplicationToGetVenue{" +
                "id:" + id +
                "status=" + status +
                ", startDate=" + startDate +
                ", endDate=" + endDate;
    }


}
