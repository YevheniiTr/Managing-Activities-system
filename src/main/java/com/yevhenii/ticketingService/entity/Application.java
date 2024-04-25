package com.yevhenii.ticketingService.entity;

import com.yevhenii.ticketingService.entity.enums.EApplicationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "application")
public class Application extends  BaseEntity {
    @Enumerated(EnumType.STRING)
    EApplicationStatus status;
    @Column(name = "org_name")
    String  orgName;
    @Column(name = "org_phone")
    String  orgPhone;
    String  city;
    @Column(name = "st_location")
    String  location;
    String  title;
    @Column(name = "begin_date")
    Date  startDate;

    @OneToOne
    @JoinColumn(name = "file_id",referencedColumnName = "id")
    ProveFile proveFile;
    @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "user_id")
    User user;

    public Application(Long id) {
        super(id);
    }
    public Application(EApplicationStatus status, String orgName, String orgPhone,String city,
                       String location,String title, Date startDate)
    {
       this.status = status;
       this.orgName = orgName;
       this.orgPhone = orgPhone;
       this.city = city;
       this.location = location;
       this.title = title;
       this.startDate = startDate;
    }
}
