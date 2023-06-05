package com.nyanband.university_organizer.controller;

import com.nyanband.university_organizer.controller.util.SecurityUtils;
import com.nyanband.university_organizer.dto.ViewProfileDTO;
import com.nyanband.university_organizer.dto.ViewProjectDTO;
import com.nyanband.university_organizer.service.ProfileService;
import com.nyanband.university_organizer.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class AdminController {

    private final ProjectService projectService;
    private final ProfileService profileService;
    private final SecurityUtils securityUtils;

    @GetMapping({"/projects"})
    public String projects(Model model) {
        if (!securityUtils.checkAdmin()) {
            return "not_admin_error";
        }
        List<ViewProjectDTO> projects = projectService.findAll();
        model.addAttribute("projects", projects);
        return "adminProjects";
    }

    @GetMapping({"/profiles"})
    public String profiles(Model model) {
        if (!securityUtils.checkAdmin()) {
            return "not_admin_error";
        }
        List<ViewProfileDTO> profiles = profileService.findAll();
        model.addAttribute("profiles", profiles);
        return "adminUsers";
    }

}
