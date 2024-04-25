package com.yevhenii.ticketingService.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "street")
public class Street extends BaseEntity {
    @Column(name = "streetname")
    private String streetName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cityID")
    City city;

    public Street(Long id) {
        super(id);
    }

    public Street(String streetName) {
        this.streetName = streetName;
    }
}
