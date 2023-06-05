package com.nyanband.university_organizer.controller;

import com.nyanband.university_organizer.controller.util.SecurityUtils;
import com.nyanband.university_organizer.controller.util.SessionUtils;
import com.nyanband.university_organizer.dto.ViewProfileDTO;
import com.nyanband.university_organizer.dto.SaveProfileDTO;
import com.nyanband.university_organizer.dto.ViewTechnologyDTO;
import com.nyanband.university_organizer.entity.enums.Gender;
import com.nyanband.university_organizer.exception.EntityNotFoundException;
import com.nyanband.university_organizer.service.ProfileService;
import com.nyanband.university_organizer.service.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/profile")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProfileController {

    ProfileService profileService;
    TechnologyService technologyService;
    SecurityUtils securityUtils;
    SessionUtils sessionUtils;

    @Autowired
    public ProfileController(ProfileService profileService,
                             TechnologyService technologyService,
                             SecurityUtils securityUtils,
                             SessionUtils sessionUtils) {
        this.profileService = profileService;
        this.technologyService = technologyService;
        this.securityUtils = securityUtils;
        this.sessionUtils = sessionUtils;
    }

    @GetMapping({"/{profileId}"})
    public String getProfile(@PathVariable Long profileId,
                             Model model) {
        ViewProfileDTO profile = profileService.find(profileId)
                .orElseThrow(() -> new EntityNotFoundException("Profile with id " + profileId + " does not exist"));
        model.addAttribute("profile", profile);
        return "profile_show";
    }

    @GetMapping({"/all"})
    public String getAllProfiles(Model model) {
        model.addAttribute("profiles", profileService.findAll());
        return "profile_all";
    }

    @GetMapping({"/me"})
    public String getMyProfile(Model model,
                               HttpSession session) {
        ViewProfileDTO profile = profileService.find((Long) sessionUtils.getProfileId(session))
                .orElseThrow(() -> new EntityNotFoundException("User does not have a profile"));
        model.addAttribute("profile", profile);

        return "profile_my";
    }

    @GetMapping({"/create"})
    public String getCreateProfilePage(Model model) {
        model.addAttribute("technologies", technologyService.findAll());
        return "profile_create";
    }

    @PostMapping({"/create"})
    public RedirectView createProfile(@RequestParam String name,
                                      @RequestParam Integer age,
                                      @RequestParam String gender,
                                      @RequestParam String git,
                                      @RequestParam String info,
                                      @RequestParam Long[] technologies,
                                      HttpSession session) {
        SaveProfileDTO saveProfileDTO = new SaveProfileDTO(
                name,
                age,
                Gender.valueOf(gender),
                git,
                info,
                securityUtils.getUserId(),
                Arrays.asList(technologies)
        );

        ViewProfileDTO savedProfile = profileService.save(saveProfileDTO);

        sessionUtils.setProfileId(session, savedProfile.getId());

        return new RedirectView("/profile/me");
    }

    @GetMapping({"/update"})
    public String getUpdateProfilePage(Model model,
                                       HttpSession session) {
        ViewProfileDTO profile = profileService.find((Long) sessionUtils.getProfileId(session))
                .orElseThrow(() -> new EntityNotFoundException("User does not have a profile"));
        model.addAttribute("profile", profile);

        List<ViewTechnologyDTO> uncheckedTechnologies = technologyService.findAll();
        uncheckedTechnologies.removeAll(profile.getTechnologies());
        model.addAttribute("technologies", uncheckedTechnologies);

        Set<Gender> uncheckedGenders = new HashSet<>(List.of(Gender.values()));
        uncheckedGenders.removeIf(g -> g.equals(profile.getGender()));
        model.addAttribute("genders", uncheckedGenders);

        return "profile_update";
    }

    @PostMapping({"/update"})
    public RedirectView updateProfile(@RequestParam String name,
                                      @RequestParam Integer age,
                                      @RequestParam String gender,
                                      @RequestParam String git,
                                      @RequestParam String info,
                                      @RequestParam Long[] technologies) {

        SaveProfileDTO saveProfileDTO = new SaveProfileDTO(
                name,
                age,
                Gender.valueOf(gender),
                git,
                info,
                securityUtils.getUserId(),
                Arrays.asList(technologies)
        );

        profileService.update(saveProfileDTO);

        return new RedirectView("/profile/me");
    }

    @PostMapping({"/ban/{id}"})
    public RedirectView ban(@PathVariable Long id) {
        profileService.banProfile(id);
        return new RedirectView("/admin/profiles");
    }

    @PostMapping({"/make_admin/{id}"})
    public RedirectView makeAdmin(@PathVariable Long id) {
        profileService.makeAdmin(id);
        return new RedirectView("/admin/profiles");
    }

}
