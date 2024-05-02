package com.yevhenii.organisationSystem.controller;

import com.yevhenii.organisationSystem.controller.util.SecurityUtils;
import com.yevhenii.organisationSystem.dto.ActivityDTO;
import com.yevhenii.organisationSystem.dto.SaveApplicationDTO;
import com.yevhenii.organisationSystem.dto.VenueDTO;
import com.yevhenii.organisationSystem.entity.ApplicationToGetVenue;
import com.yevhenii.organisationSystem.entity.City;
import com.yevhenii.organisationSystem.entity.Edge;
import com.yevhenii.organisationSystem.entity.Venue;
import com.yevhenii.organisationSystem.repository.CityRepository;
import com.yevhenii.organisationSystem.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
public class ApplicationController {

    ApplicationService applicationService;
    ActivityService activityService;
    SecurityUtils securityUtils;
    VenueService venueService;
    CityService cityService;
    EdgeService edgeService;

    @Autowired
    public ApplicationController(EdgeService edgeService, CityService cityService, VenueService venueService, SecurityUtils securityUtils, ApplicationService applicationService, ActivityService activityService) {
        this.applicationService = applicationService;
        this.activityService = activityService;
        this.securityUtils = securityUtils;
        this.venueService = venueService;
        this.cityService = cityService;
        this.edgeService = edgeService;
    }

    @GetMapping("/sendApplicationForm")
    public String getCreateApplicationForm(Model model) {

        List<ActivityDTO> activityDTOList = activityService.findAllByUserId(securityUtils.getUserId());
        model.addAttribute("activityList", activityDTOList);

        if (model.containsAttribute("filteredList")) {
            model.addAttribute("venueList", model.getAttribute("filteredList"));
        } else {
            List<Venue> freeVenueList = venueService.findAllFreeVenuesForCurrentDate();
            model.addAttribute("venueList", freeVenueList);
        }
        List<City> cityList = cityService.findAll();
        model.addAttribute("cityList", cityList);
        return "sendApplicationForm";
    }

    @GetMapping("/getOwnerApplications")
    public String getOwnerApplications(Model model) {
        //Page<Edge> edgesPage = applicationService.findPaginated(securityUtils.getUserId(), PageRequest.of(page - 1, size));
        model.addAttribute("venueList", venueService.findAllById(securityUtils.getUserId()));
        if (model.containsAttribute("filteredOwnerList")) {
            model.addAttribute("ownerEdges", model.getAttribute("filteredOwnerList"));
        } else {
            model.addAttribute("ownerEdges", applicationService.findAllForOwner(securityUtils.getUserId()));
        }
        return "ownerApplications";
    }

    @GetMapping("/getOrganisatorApplication")
    public String getOrganisatorApplications(Model model) {
        //Page<Edge> edgesPage = applicationService.findPaginated(securityUtils.getUserId(), PageRequest.of(page - 1, size));
        model.addAttribute("activityList", activityService.findAllByUserId(securityUtils.getUserId()));
        model.addAttribute("orgApplications", edgeService.findOrganisatorApplicationsByUserId(securityUtils.getUserId()));
        if (model.containsAttribute("filteredOrganisatorList")) {
            model.addAttribute("orgApplications", model.getAttribute("filteredOrganisatorList"));
        } else {
            model.addAttribute("orgApplications", edgeService.findOrganisatorApplicationsByUserId(securityUtils.getUserId()));
        }
        return "organisatorApplications";
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
            applicationService.sendApplication(saveApplication, venueList);
            return new RedirectView("/index");
        } else {
            throw new AccessDeniedException("Application does not exist or user dont have access on it");
        }
    }

    @GetMapping({"/venueHolderApplications/decline/{id}"})
    public RedirectView decline(@PathVariable Long id) {
        applicationService.decline(id);
        return new RedirectView("/getOwnerApplications");
    }

    @GetMapping({"/venueHolderApplications/approve/{id}"})
    public RedirectView approve(@PathVariable Long id) {
        applicationService.approve(id);
        return new RedirectView("/getOwnerApplications");
    }

//    @PostMapping("/filter")
//    public RedirectView filterVenues(
//            @RequestParam(required = true) String filterDate,
//            @RequestParam(required = false) String filterCity,
//            @RequestParam(required = false) Double filterPrice,
//            @RequestParam(required = false) Integer filterCapacity,
//            RedirectAttributes redirectAttributes) {
//        List<Venue> listForFilter;
//        System.out.println(filterDate);
//        if (filterDate != null) {
//            Timestamp date = Timestamp.valueOf(filterDate + " " + "00:00:00");
//            listForFilter = venueService.findAllFreeVenuesForDate(date);
//
//        } else {
//            listForFilter = venueService.findAllFreeVenuesForCurrentDate();
//        }
//        List<Venue> filteredList = listForFilter.stream()
//                .filter(venue -> (filterCity == null || venue.getStreet().getCity().getCityName().equalsIgnoreCase(filterCity)))
//                .filter(venue -> (filterPrice == null || venue.getRentPrice() >= filterPrice))
//                .filter(venue -> (filterCapacity == null || venue.getMaximumSeats() >= filterCapacity))
//                .collect(Collectors.toList());
//        redirectAttributes.addFlashAttribute("filteredList", filteredList);
//        return new RedirectView("/sendApplicationForm");
//    }
//
//    @PostMapping("/ownerFilter")
//    public RedirectView filterEdgesForVenues(
//            @RequestParam(required = true) String filterDate,
//            @RequestParam String filterVenue,
//            RedirectAttributes redirectAttributes) {
//        List<Edge> listForFilter;
//        System.out.println(filterDate);
//
//        //Timestamp date = Timestamp.valueOf(filterDate +" " + "00:00:00");
//        LocalDate localDate = LocalDate.parse(filterDate);
//        System.out.println(localDate);
//        listForFilter = edgeService.findUserEdgesForDate(localDate, securityUtils.getUserId());
//        System.out.println(listForFilter);
//
////        else{
////            listForFilter = applicationService.findAllForOwner(securityUtils.getUserId());
////        }
//        List<Edge> filteredList = listForFilter.stream()
//                .filter(edge -> (filterVenue == null || edge.getVenue().getTitle().equalsIgnoreCase(filterVenue)))
//                .collect(Collectors.toList());
//        System.out.println(filteredList);
//        redirectAttributes.addFlashAttribute("filteredOwnerList", filteredList);
//        return new RedirectView("/getOwnerApplications");
//    }
//
//    @PostMapping("/organisatorFilter")
//    public RedirectView filterEdgesForApplication(
//            @RequestParam(required = true) String filterDate,
//            @RequestParam String filterActivity,
//            RedirectAttributes redirectAttributes) {
//        List<Edge> listForFilter;
//        System.out.println(filterDate);
//        LocalDate localDate = LocalDate.parse(filterDate);
//        System.out.println(localDate);
//        listForFilter = edgeService.findEdgesByOrganisatorAndDate(localDate, securityUtils.getUserId());
//        System.out.println(listForFilter);
//        List<Edge> filteredList = listForFilter.stream()
//                .filter(edge -> (filterActivity == null || edge.getApplicationToGetVenue().getActivity().getTitle().equalsIgnoreCase(filterActivity)))
//                .collect(Collectors.toList());
//        System.out.println(filteredList);
//        redirectAttributes.addFlashAttribute("filteredOrganisatorList", filteredList);
//        return new RedirectView("/getOrganisatorApplication");
//    }







    @GetMapping("/applications/delete/{edgeId}")
    public RedirectView deleteVenue(@PathVariable Long edgeId) {
        applicationService.delete(edgeId);
        return new RedirectView("/getOrganisatorApplication");
    }


}
