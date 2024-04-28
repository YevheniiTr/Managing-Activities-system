package com.yevhenii.organisationSystem.services.serviceImpl;

import com.yevhenii.organisationSystem.controller.util.SecurityUtils;
import com.yevhenii.organisationSystem.dto.ActivityDTO;
import com.yevhenii.organisationSystem.dto.mapper.ApplicationMapper;
import com.yevhenii.organisationSystem.dto.SaveActivityDTO;
import com.yevhenii.organisationSystem.entity.Activity;
import com.yevhenii.organisationSystem.entity.User;
import com.yevhenii.organisationSystem.repository.ActivityRepository;
import com.yevhenii.organisationSystem.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class ActivityServiceImpl  implements ActivityService {

    ActivityRepository activityRepository;
    ApplicationMapper applicationMapper;
    SecurityUtils securityUtils;
    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository, ApplicationMapper applicationMapper,SecurityUtils securityUtils) {
        this.activityRepository = activityRepository;
        this.applicationMapper = applicationMapper;
        this.securityUtils = securityUtils;
    }

    @Override
    public void save(SaveActivityDTO saveActivityDTO) {
        Activity activity = applicationMapper.SaveActivityDTOtoEntity(saveActivityDTO);
        Long userId = securityUtils.getUserId();
        activity.setUser(new User(userId));
        activityRepository.save(activity);
    }

    @Override
    public boolean isActivityBelongToUser(Long userId, Long activityId) {
        return activityRepository.isActivityBelongToUser(userId, activityId);
    }

    @Override
    public void delete(Long activityId) {
        activityRepository.deleteById(activityId);
    }

    @Override
    public boolean isActivityBelongToUserByTitle(Long userId, String activityTitle) {
        return activityRepository.isActivityBelongToUserByTitle(userId,activityTitle);
    }

    @Override
    public List<ActivityDTO> findAllByUserId(Long userId) {
        return activityRepository.findAllByUserId(userId)
                .stream()
                .map(applicationMapper::activityToDto)
                .collect(toList());
    }
}
