package com.nyanband.university_organizer.controller;

import com.nyanband.university_organizer.controller.util.SecurityUtils;
import com.nyanband.university_organizer.controller.util.SessionUtils;
import com.nyanband.university_organizer.security.pojo.SignUpRequest;
import com.nyanband.university_organizer.service.ProfileService;
import com.nyanband.university_organizer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {

    SecurityUtils securityUtils;
    SessionUtils sessionUtils;
    UserService userService;
    ProfileService profileService;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(SecurityUtils securityUtils,
                                    SessionUtils sessionUtils,
                                    UserService userService,
                                    ProfileService profileService,
                                    PasswordEncoder passwordEncoder,
                                    AuthenticationManager authenticationManager) {
        this.securityUtils = securityUtils;
        this.sessionUtils = sessionUtils;
        this.userService = userService;
        this.profileService = profileService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping({"/index", "/"})
    public String index() {
        return "index";
    }

    @GetMapping({"/register"})
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public RedirectView login(@RequestParam String email,
                              @RequestParam String password,
                              HttpSession session) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        profileService
                .findByUserId(securityUtils.getUserId())
                .ifPresent(profile -> sessionUtils.setProfileId(session, profile.getId()));

        return new RedirectView("/profile/me");
    }

    @PostMapping("/register")
    public RedirectView register(@RequestParam String email,
                                 @RequestParam String password) {
        userService.register(new SignUpRequest(
                email,
                passwordEncoder.encode(password)
        ));
        return new RedirectView("login");
    }


}
