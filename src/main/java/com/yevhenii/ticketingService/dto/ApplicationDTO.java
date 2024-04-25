package com.yevhenii.ticketingService.dto;

import com.yevhenii.ticketingService.entity.enums.EApplicationStatus;
import lombok.Value;

import java.util.Date;

@Value
public class ApplicationDTO {
    Long id;
    EApplicationStatus status;
    String  orgName;
    String  orgPhone;
    String  city;
    String  location;
    String  title;
    Date  startDate;
    String fileId;

}
