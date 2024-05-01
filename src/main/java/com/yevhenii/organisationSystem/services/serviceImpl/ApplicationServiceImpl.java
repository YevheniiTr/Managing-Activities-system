package com.yevhenii.organisationSystem.services.serviceImpl;

import com.yevhenii.organisationSystem.controller.util.SecurityUtils;
import com.yevhenii.organisationSystem.dto.SaveApplicationDTO;
import com.yevhenii.organisationSystem.dto.VenueDTO;
import com.yevhenii.organisationSystem.dto.mapper.ApplicationMapper;
import com.yevhenii.organisationSystem.entity.Activity;
import com.yevhenii.organisationSystem.entity.ApplicationToGetVenue;
import com.yevhenii.organisationSystem.entity.Edge;
import com.yevhenii.organisationSystem.entity.Venue;
import com.yevhenii.organisationSystem.entity.enums.EApplicationStatus;
import com.yevhenii.organisationSystem.repository.ActivityRepository;
import com.yevhenii.organisationSystem.repository.ApplicationRepository;
import com.yevhenii.organisationSystem.repository.EdgeRepository;
import com.yevhenii.organisationSystem.repository.VenueRepository;
import com.yevhenii.organisationSystem.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    ActivityRepository activityRepository;
    ApplicationRepository applicationRepository;
    VenueRepository venueRepository;
    EdgeRepository edgeRepository;
    SecurityUtils securityUtils;
    ApplicationMapper applicationMapper;

    @Autowired
    public ApplicationServiceImpl(ApplicationMapper applicationMapper, ActivityRepository activityRepository, ApplicationRepository applicationRepository, VenueRepository venueRepository, EdgeRepository edgeRepository, SecurityUtils securityUtils) {
        this.activityRepository = activityRepository;
        this.applicationRepository = applicationRepository;
        this.venueRepository = venueRepository;
        this.edgeRepository = edgeRepository;
        this.securityUtils = securityUtils;
        this.applicationMapper = applicationMapper;
    }


    @Override
    public void save(SaveApplicationDTO saveApplicationDTO) {
        Long userId = securityUtils.getUserId();
        ApplicationToGetVenue application = new ApplicationToGetVenue();
        application.setStatus(EApplicationStatus.EXPECT);
        application.setEndDate(saveApplicationDTO.getFinishTime());
        application.setStartDate(saveApplicationDTO.getStartTime());
        Activity activity = activityRepository.findByActivityTitle(saveApplicationDTO.getActivityTitle(), userId);
        application.setActivity(activity);
        applicationRepository.save(application);
    }

    @Override
    public boolean isApplicationBelongToUser(Long userId, Long applicationId) {
        return applicationRepository.isApplicationBelongToUser(userId, applicationId);
    }

    @Override
    public void delete(Long applicationId) {
        edgeRepository.deleteAll(edgeRepository.findByApplicationId(applicationId));
        applicationRepository.deleteById(applicationId);
    }

    @Override
    public void sendApplication(SaveApplicationDTO saveApplicationDTO, List<Venue> venueList) {
        Long userId = securityUtils.getUserId();
        ApplicationToGetVenue application = new ApplicationToGetVenue();
        application.setStatus(EApplicationStatus.EXPECT);
        application.setEndDate(saveApplicationDTO.getFinishTime());
        application.setStartDate(saveApplicationDTO.getStartTime());
        Activity activity = activityRepository.findByActivityTitle(saveApplicationDTO.getActivityTitle(), userId);
        application.setActivity(activity);
        applicationRepository.save(application);
        for (Venue venue : venueList) {
            Edge edge = new Edge();
            edge.setVenue(venue);
            edge.setApplicationToGetVenue(application);
            edge.setDate(application.getStartDate());
            edgeRepository.save(edge);
        }
    }

    @Override
    public void approve(Long applicationId) {
        Optional<ApplicationToGetVenue> applicationToGetVenue = applicationRepository.findById(applicationId);
        ApplicationToGetVenue application = applicationToGetVenue.get();
        application.setStatus(EApplicationStatus.APPROVED);
        applicationRepository.save(application);
    }

    @Override
    public void decline(Long applicationId) {
        Optional<ApplicationToGetVenue> applicationToGetVenue = applicationRepository.findById(applicationId);
        ApplicationToGetVenue application = applicationToGetVenue.get();
        application.setStatus(EApplicationStatus.DECLINED);
        applicationRepository.save(application);
    }

    @Override
    public List<ApplicationToGetVenue> findOrganisatorApplicationsByUserId(long userId) {
        return applicationRepository.findOrganisatorApplicationsByUserId(userId);
    }

    @Override
    public List<Edge> findAllForOwner(long userId) {
        return applicationRepository.findAllForOwner(userId);
    }
}
