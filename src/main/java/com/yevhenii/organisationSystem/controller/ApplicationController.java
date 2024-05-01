package com.yevhenii.organisationSystem.controller;

import com.yevhenii.organisationSystem.controller.util.SecurityUtils;
import com.yevhenii.organisationSystem.dto.ActivityDTO;
import com.yevhenii.organisationSystem.dto.SaveApplicationDTO;
import com.yevhenii.organisationSystem.dto.VenueDTO;
import com.yevhenii.organisationSystem.entity.ApplicationToGetVenue;
import com.yevhenii.organisationSystem.entity.City;
import com.yevhenii.organisationSystem.entity.Venue;
import com.yevhenii.organisationSystem.repository.CityRepository;
import com.yevhenii.organisationSystem.services.ActivityService;
import com.yevhenii.organisationSystem.services.ApplicationService;
import com.yevhenii.organisationSystem.services.CityService;
import com.yevhenii.organisationSystem.services.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class ApplicationController {

    ApplicationService applicationService;
    ActivityService activityService;
    SecurityUtils securityUtils;
    VenueService venueService;
    CityService cityService;

    @Autowired
    public ApplicationController(CityService cityService,VenueService venueService, SecurityUtils securityUtils, ApplicationService applicationService, ActivityService activityService) {
        this.applicationService = applicationService;
        this.activityService = activityService;
        this.securityUtils = securityUtils;
        this.venueService = venueService;
        this.cityService = cityService;
    }

    @GetMapping("/sendApplicationForm")
    public String getCreateApplicationForm(Model model) {

        List<ActivityDTO> activityDTOList = activityService.findAllByUserId(securityUtils.getUserId());
        model.addAttribute("activityList", activityDTOList);

        if (model.containsAttribute("filteredList")) {
            model.addAttribute("venueList", model.getAttribute("filteredList"));
        } else {
            //List<Venue> venueList = venueService.findAll();
            List<Venue> freeVenueList = venueService.findAllFreeVenuesForCurrentDate();
            model.addAttribute("venueList", freeVenueList);
        }
        List<City> cityList = cityService.findAll();
        model.addAttribute("cityList",cityList);
        return "sendApplicationForm";
    }
    @GetMapping("/applications/my")
    public String getUserApplications(Model model){
        model.addAttribute("orgApplications",applicationService.findOrganisatorApplicationsByUserId(securityUtils.getUserId()));
        model.addAttribute("ownerEdges",applicationService.findAllForOwner(securityUtils.getUserId()));
        System.out.println(applicationService.findOrganisatorApplicationsByUserId(securityUtils.getUserId()));

        return "userApplications";
    }

    @PostMapping("/applications/my")
    public RedirectView getUserApplicationsOnRole(@RequestParam String role,Model model,RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("role", role);
        return  new RedirectView("/applications/my");
    }





    @PostMapping("/application/create")
    public RedirectView createApplication(@RequestBody SaveApplicationDTO saveApplication) {

        if (activityService.isActivityBelongToUserByTitle(securityUtils.getUserId(), saveApplication.getActivityTitle())) {
            applicationService.save(saveApplication);
            return new RedirectView("/application/my");
        } else {
            throw new AccessDeniedException("Venue does not exist or user dont have access on it");
        }
    }


    @PostMapping("/sendApplication")
    public RedirectView sendApplication(@RequestParam String startDate,
                                        @RequestParam String startTimeHour,
                                        @RequestParam String startTimeMin,
                                        @RequestParam String endDate,
                                        @RequestParam String endTimeHour,
                                        @RequestParam String endTimeMin,
                                        @RequestParam String activityTitle,
                                        @RequestParam List<Venue> venueList
                                        ) {
        String startTime = startTimeHour + ":" + startTimeMin;
        String endTime = endTimeHour + ":" + endTimeMin;
        SaveApplicationDTO saveApplication = new SaveApplicationDTO(startDate, startTime, endDate, endTime, activityTitle);
        if (activityService.isActivityBelongToUserByTitle(securityUtils.getUserId(), saveApplication.getActivityTitle())) {
            //applicationService.save(saveApplication);
            applicationService.sendApplication(saveApplication, venueList);
            return new RedirectView("/index");
        } else {
            throw new AccessDeniedException("Application does not exist or user dont have access on it");
        }
    }

    @PostMapping({"/venueHolderApplications/decline/{id}"})
    public RedirectView decline(@PathVariable Long id) {
        applicationService.decline(id);
        return new RedirectView("/index");
    }

    @PostMapping({"/venueHolderApplications/approve/{id}"})
    public RedirectView approve(@PathVariable Long id) {
        applicationService.approve(id);
        return new RedirectView("/index");
    }
    @PostMapping("/filter")
    public RedirectView filterVenues(
            @RequestParam(required = true) String filterDate,
            @RequestParam(required = false) String filterCity,
            @RequestParam(required = false) Double filterPrice,
            @RequestParam(required = false) Integer filterCapacity,
            RedirectAttributes redirectAttributes) {
        List<Venue> listForFilter;
        System.out.println(filterDate);
        if(filterDate != null){
            Timestamp date = Timestamp.valueOf(filterDate +" " + "00:00:00");
            listForFilter = venueService.findAllFreeVenuesForDate(date);

        }
        else{
            listForFilter = venueService.findAllFreeVenuesForCurrentDate();
        }
        List<Venue> filteredList = listForFilter.stream()
                .filter(venue -> (filterCity == null || venue.getStreet().getCity().getCityName().equalsIgnoreCase(filterCity)))
                .filter(venue -> (filterPrice == null || venue.getRentPrice() >= filterPrice))
                .filter(venue -> (filterCapacity == null || venue.getMaximumSeats() >= filterCapacity))
                .collect(Collectors.toList());
        redirectAttributes.addFlashAttribute("filteredList", filteredList);
        return  new RedirectView("/sendApplicationForm");
    }

    @GetMapping("/applications/delete/{applicationId}")
    public RedirectView deleteVenue(@PathVariable Long applicationId) {
        applicationService.delete(applicationId);
        return new RedirectView("/applications/my");
    }



}
