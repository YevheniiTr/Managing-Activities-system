package com.yevhenii.ticketingService.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="edge")
public class Edge extends  BaseEntity{
    @Column(name ="startdate")
    private Timestamp date;
    @Column(name ="ismatching")
    private boolean isMatching;
    @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name ="venueid")
    Venue venue;
    @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "applvenueid")
    ApplicationToGetVenue applicationToGetVenue;

    public Edge(Long id) {
        super(id);
    }

    public Edge(Long id, Timestamp date, boolean isMatching) {
        super(id);
        this.date = date;
        this.isMatching = isMatching;
    }

//    public void setMatching(boolean matching) {
//        isMatching = matching;
//    }

    @Override
    public String toString() {
        return "Edge{" +
                "date=" + date +
                ", isMatching=" + isMatching +
                ", venue=" + venue +
                ", applicationToGetVenue=" + applicationToGetVenue +
                '}';
    }
}
