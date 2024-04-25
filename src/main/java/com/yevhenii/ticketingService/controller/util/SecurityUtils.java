package com.yevhenii.ticketingService.controller.util;

import com.yevhenii.ticketingService.security.userdetails.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityUtils {

//    ProfileService profileService;
//
//    @Autowired
//    public SecurityUtils(ProfileService profileService) {
//        this.profileService = profileService;
//    }

    public long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((UserDetailsImpl) authentication.getPrincipal()).getId();
    }

    public boolean checkAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<? extends GrantedAuthority> maybeAdminRole = ((UserDetailsImpl) authentication.getPrincipal()).getAuthorities().stream()
                .filter(a -> a.getAuthority().equals("ADMIN"))
                .findFirst();

        return maybeAdminRole.isPresent();
    }

}
