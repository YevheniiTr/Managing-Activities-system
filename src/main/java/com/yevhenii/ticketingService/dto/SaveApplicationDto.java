package com.yevhenii.ticketingService.dto;

import com.yevhenii.ticketingService.entity.enums.EApplicationStatus;
import lombok.Value;

import java.util.Date;
@Value
public class SaveApplicationDto {

    String  orgName;
    String  orgPhone;
    String  city;
    String  location;
    String  title;
    Date  startDate;
    EApplicationStatus Status;
    Long userId;
    String fileId;

}
//    EApplicationStatus status;
//    @Column(name = "org_name")
//    String  orgName;
//    @Column(name = "org_phone")
//    String  orgPhone;
//    String  city;
//    @Column(name = "st_location")
//    String  location;
//    String  title;
//    @Column(name = "begin_date")
//    Date    startDate;
//    //    @OneToOne(mappedBy = "application", fetch = FetchType.LAZY)
////    ProveFile proveFile;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    User user;