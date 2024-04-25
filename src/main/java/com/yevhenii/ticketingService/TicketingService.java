package com.yevhenii.ticketingService;

import com.yevhenii.ticketingService.entity.*;
import com.yevhenii.ticketingService.repository.EdgeRepository;
import com.yevhenii.ticketingService.service.impl.KunAlgorithmImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SpringBootApplication

public class TicketingService {

    public static void main(String[] args) {
        SpringApplication.run(TicketingService.class, args);
    }

}