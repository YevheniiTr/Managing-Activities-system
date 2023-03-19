package com.nyanband.university_organizer.controller.util;

import com.nyanband.university_organizer.security.userdetails.UserDetailsImpl;
import com.nyanband.university_organizer.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    ProfileService profileService;

    @Autowired
    public SecurityUtils(ProfileService profileService) {
        this.profileService = profileService;
    }

    public long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((UserDetailsImpl) authentication.getPrincipal()).getId();
    }

}
