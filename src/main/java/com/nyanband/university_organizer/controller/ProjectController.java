package com.nyanband.university_organizer.controller;

import com.nyanband.university_organizer.controller.util.SecurityUtils;
import com.nyanband.university_organizer.dto.SaveProjectDTO;
import com.nyanband.university_organizer.dto.ViewProjectDTO;
import com.nyanband.university_organizer.exception.EntityNotFoundException;
import com.nyanband.university_organizer.service.ProjectService;
import com.nyanband.university_organizer.service.TechnologyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.Arrays;

@Controller
@RequestMapping("/project")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProjectController {

    ProjectService projectService;
    TechnologyService technologyService;
    SecurityUtils securityUtils;

    public ProjectController(ProjectService projectService,
                             TechnologyService technologyService,
                             SecurityUtils securityUtils) {
        this.projectService = projectService;
        this.technologyService = technologyService;
        this.securityUtils = securityUtils;
    }

    @GetMapping({"/{projectId}"})
    public String getProject(@PathVariable Long projectId,
                             Model model) {
        ViewProjectDTO project = projectService.find(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project with id " + projectId + " does not exist"));
        model.addAttribute("project", project);
        return "project_show";
    }

    @GetMapping({"/all"})
    public String getAllProfiles(Model model) {
        model.addAttribute("projects", projectService.findAll());
        return "project_search";
    }

    @GetMapping({"/create"})
    public String getCreateProfilePage(Model model) {
        model.addAttribute("technologies", technologyService.findAll());
        return "project_create";
    }

    @PostMapping({"/create"})
    public RedirectView createProfile(@RequestParam String name,
                                      @RequestParam String description,
                                      @RequestParam Long[] technologies) {
        SaveProjectDTO saveProjectDTO = new SaveProjectDTO(
                name,
                description,
                LocalDateTime.now(),
                securityUtils.getUserId(),
                Arrays.asList(technologies)
        );

        ViewProjectDTO savedProject = projectService.save(saveProjectDTO);

        return new RedirectView("/project/" + savedProject.getId());
    }

//    @GetMapping({"/update"})
//    public String getUpdateProfilePage(Model model,
//                                       HttpSession session) {
//        ViewProfileDTO profile = projectService.find((Long) sessionUtils.getProfileId(session))
//                .orElseThrow(() -> new EntityNotFoundException("User does not have a profile"));
//        model.addAttribute("profile", profile);
//
//        List<ViewTechnologyDTO> uncheckedTechnologies = technologyService.findAll();
//        uncheckedTechnologies.removeAll(profile.getTechnologies());
//        model.addAttribute("technologies", uncheckedTechnologies);
//
//        Set<Gender> uncheckedGenders = new HashSet<>(List.of(Gender.values()));
//        uncheckedGenders.removeIf(g -> g.equals(profile.getGender()));
//        model.addAttribute("genders", uncheckedGenders);
//
//        return "profile_update";
//    }

//    @PostMapping({"/update"})
//    public RedirectView updateProfile(@RequestParam String name,
//                                      @RequestParam Integer age,
//                                      @RequestParam String gender,
//                                      @RequestParam String git,
//                                      @RequestParam String info,
//                                      @RequestParam Long[] technologies) {
//
//        SaveProfileDTO saveProfileDTO = new SaveProfileDTO(
//                name,
//                age,
//                Gender.valueOf(gender),
//                git,
//                info,
//                securityUtils.getUserId(),
//                Arrays.asList(technologies)
//        );
//
//        projectService.update(saveProfileDTO);
//
//        return new RedirectView("/profile/me");
//    }

}
