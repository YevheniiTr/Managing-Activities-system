package com.yevhenii.organisationSystem.services.serviceImpl;

import com.yevhenii.organisationSystem.controller.util.SecurityUtils;
import com.yevhenii.organisationSystem.dto.SaveApplicationDTO;
import com.yevhenii.organisationSystem.entity.Activity;
import com.yevhenii.organisationSystem.entity.ApplicationToGetVenue;
import com.yevhenii.organisationSystem.entity.Edge;
import com.yevhenii.organisationSystem.entity.Venue;
import com.yevhenii.organisationSystem.entity.enums.EApplicationStatus;
import com.yevhenii.organisationSystem.payload.request.SendApplicationRequest;
import com.yevhenii.organisationSystem.repository.ActivityRepository;
import com.yevhenii.organisationSystem.repository.ApplicationRepository;
import com.yevhenii.organisationSystem.repository.EdgeRepository;
import com.yevhenii.organisationSystem.repository.VenueRepository;
import com.yevhenii.organisationSystem.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    ActivityRepository activityRepository;
    ApplicationRepository applicationRepository;
    VenueRepository venueRepository;
    EdgeRepository edgeRepository;
    SecurityUtils securityUtils;
    @Autowired
    public ApplicationServiceImpl(ActivityRepository activityRepository, ApplicationRepository applicationRepository, VenueRepository venueRepository, EdgeRepository edgeRepository, SecurityUtils securityUtils) {
        this.activityRepository = activityRepository;
        this.applicationRepository = applicationRepository;
        this.venueRepository = venueRepository;
        this.edgeRepository = edgeRepository;
        this.securityUtils = securityUtils;
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
    public void delete(Long activityId) {

    }

    @Override
    public void sendApplication(SaveApplicationDTO saveApplicationDTO, SendApplicationRequest sendApplicationRequest) {

        Long userId = securityUtils.getUserId();
        ApplicationToGetVenue application = new ApplicationToGetVenue();
        application.setStatus(EApplicationStatus.EXPECT);
        application.setEndDate(saveApplicationDTO.getFinishTime());
        application.setStartDate(saveApplicationDTO.getStartTime());
        Activity activity = activityRepository.findByActivityTitle(saveApplicationDTO.getActivityTitle(), userId);
        application.setActivity(activity);
        applicationRepository.save(application);
        for (String title : sendApplicationRequest.getVenueTitle()) {
            System.out.println(title);
            Venue venue = venueRepository.findByVenueTitle(title);
            Edge edge = new Edge();
            edge.setVenue(venue);
            edge.setApplicationToGetVenue(application);
            edge.setDate(application.getStartDate());
            edgeRepository.save(edge);
        }
    }


    @Override
    public void approve() {

    }

    @Override
    public void decline() {

    }
}
