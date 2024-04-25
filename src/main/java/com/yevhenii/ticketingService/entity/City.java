package com.yevhenii.ticketingService.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "city")
public class City extends BaseEntity {

    @Column(name = "cityname")
    private String cityName;
    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    List<Street> streetList;
    @OneToMany(mappedBy ="city",fetch = FetchType.LAZY)
    List<Venue> venueList;
    public City(Long id) {
        super(id);
    }

    public City(String cityName) {
        this.cityName = cityName;
    }
}
