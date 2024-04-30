package com.yevhenii.organisationSystem.controller;

import com.yevhenii.organisationSystem.dto.VenueDTO;
import com.yevhenii.organisationSystem.dto.mapper.ApplicationMapper;
import com.yevhenii.organisationSystem.entity.Venue;
import com.yevhenii.organisationSystem.services.VenueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Припускаючи, що ви використовуєте Spring Boot і у вас є сервіс для роботи з майданчиками
@RestController
//@RequestMapping("/filter")
public class FilterController {

    private final VenueService venueService; // Сервіс, що надає дані про майданчики
    ApplicationMapper applicationMapper;
    public FilterController(VenueService venueService,ApplicationMapper applicationMapper) {
        this.venueService = venueService;
        this.applicationMapper = applicationMapper;
    }

    @PostMapping
    public ResponseEntity<?> filterVenues(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Integer capacity,
    Model model) {
        // Отримуємо всі майданчики
        List<Venue> allVenues = venueService.findAll();
        System.out.println(city + price + capacity);
        // Фільтруємо за параметрами, якщо вони вказані
        List<Venue> filteredList = allVenues.stream()
                //.filter(venue -> (date == null || venue.ge.equals(date)))
                .filter(venue -> (city == null || venue.getStreet().getCity().getCityName().equalsIgnoreCase(city)))
                .filter(venue -> (price == null || venue.getRentPrice() >= price))
                .filter(venue -> (capacity == null || venue.getMaximumSeats() >= capacity))
                .collect(Collectors.toList());
        List<VenueDTO> venueDTOList = new ArrayList<>();
        for(Venue venue: filteredList){
            venueDTOList.add(applicationMapper.venueToDTO(venue));
        }
        System.out.println(venueDTOList);
        model.addAttribute("filteredList",venueDTOList);
        // Повертаємо відфільтрований список у форматі JSON
        return ResponseEntity.ok().body(Map.of("filteredList", venueDTOList));
    }
}
