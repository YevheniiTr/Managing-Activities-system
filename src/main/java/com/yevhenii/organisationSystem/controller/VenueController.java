package com.yevhenii.organisationSystem.controller;

import com.yevhenii.organisationSystem.controller.util.SecurityUtils;
import com.yevhenii.organisationSystem.dto.SaveVenueDTO;
import com.yevhenii.organisationSystem.dto.VenueDTO;
import com.yevhenii.organisationSystem.entity.City;
import com.yevhenii.organisationSystem.entity.Street;
import com.yevhenii.organisationSystem.services.CityService;
import com.yevhenii.organisationSystem.services.VenueService;
import com.yevhenii.organisationSystem.services.serviceImpl.StreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class VenueController {

    SecurityUtils securityUtils;
    VenueService venueService;
    CityService cityService;
    StreetService streetService;

    @Autowired
    public VenueController(VenueService venueService, SecurityUtils securityUtils, CityService cityService, StreetService streetService) {
        this.venueService = venueService;
        this.securityUtils = securityUtils;
        this.cityService = cityService;
        this.streetService = streetService;
    }

    @PostMapping("/venues/createVenue")
    public RedirectView addVenue(
            @RequestParam String title,
            @RequestParam int maximumSeats,
            @RequestParam String description,
            @RequestParam String adresIndex,
            @RequestParam int rentPrice,
            @RequestParam String streetName,
            @RequestParam String cityName
           ) {
        SaveVenueDTO saveVenueDTO = new SaveVenueDTO(title, maximumSeats, description, adresIndex, rentPrice, streetName, cityName);
        venueService.save(saveVenueDTO);
        return new RedirectView("/venues");
    }

    @GetMapping("/venues/create")
    public String getCreateVenueForm(Model model) {
        List<City> cityList = cityService.findAll();
        model.addAttribute("cityList", cityList);
        List<Street> streetList = streetService.findAll();
        model.addAttribute("streetList", streetList);
        return "createVenue";
    }


    @GetMapping("/venues/{venueId}")
    public String getVenueInfo(@PathVariable Long venueId, Model model) {
        VenueDTO VenueDTO = venueService.findById(venueId).
                orElseThrow(() -> new EntityNotFoundException("Venue with id " + venueId + " does not exist"));
        model.addAttribute("venue", VenueDTO);
        return "venueDetails";
    }

    @GetMapping({"/venues/getUpdateForm/{venueId}"})
    public String getUpdateVenuePage(@PathVariable Long venueId, Model model) {
        VenueDTO venueDTO = venueService.findById(venueId)
                .orElseThrow(() -> new EntityNotFoundException("User does not have a venues"));
        model.addAttribute("venue", venueDTO);
        return "venueUpdate";
    }


    @GetMapping("/venues")
    public String getUserVenues(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) System.out.println("не Авторизований");
        long userId = securityUtils.getUserId();
        System.out.println(userId);
        model.addAttribute("userVenuesList", venueService.findAllById(userId));
        return "userVenues";
    }

    @GetMapping("/venues/delete/{venueId}")
    public RedirectView deleteVenue(@PathVariable Long venueId) {
        venueService.delete(venueId);
        return new RedirectView("/venues");
    }

    @PostMapping({"/venues/update/{venueId}"})
    public RedirectView updateVenue(@PathVariable Long venueId,
                                    @RequestParam String title,
                                    @RequestParam int maximumSeats,
                                    @RequestParam String description,
                                    @RequestParam String adresIndex,
                                    @RequestParam int rentPrice,
                                    @RequestParam String streetName,
                                    @RequestParam String cityName) {
        SaveVenueDTO venueDTO = new SaveVenueDTO(title, maximumSeats, description, adresIndex, rentPrice, streetName, cityName);
        venueService.update(venueDTO, venueId);
        return new RedirectView("/venues");
    }


}
