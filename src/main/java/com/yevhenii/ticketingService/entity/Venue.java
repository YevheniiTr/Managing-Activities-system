package com.yevhenii.ticketingService.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "venue")
public class Venue extends BaseEntity {

    @Column(name = "title")
    private String title;
    @Column(name = "maximumseats")
    private int maximumSeats;
    @Column(name = "description")
    private String description;
    @Column(name = "adresindex")
    private String adresIndex;
    @Column(name = "rentprice")
    private int rentPrice;
    @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "cityID")
    City city;
    @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "userID")
    User user;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "venue")
    List <Edge> edgeList;

    public Venue(Long id) {
        super(id);
    }

    public Venue(String title, int maximumSeats, String description, String adresIndex, int rentPrice) {
        this.title = title;
        this.maximumSeats = maximumSeats;
        this.description = description;
        this.adresIndex = adresIndex;
        this.rentPrice = rentPrice;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "title='" + title + '\'' +
                ", maximumSeats=" + maximumSeats +
                ", description='" + description + '\'' +
                ", adresIndex='" + adresIndex + '\'' +
                ", rentPrice=" + rentPrice +
                '}';
    }


}
