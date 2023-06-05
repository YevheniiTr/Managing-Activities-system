package com.nyanband.university_organizer.controller;

import com.nyanband.university_organizer.controller.util.SecurityUtils;
import com.nyanband.university_organizer.dto.SaveProjectDTO;
import com.nyanband.university_organizer.dto.ViewProjectDTO;
import com.nyanband.university_organizer.exception.EntityNotFoundException;
import com.nyanband.university_organizer.service.ProfileService;
import com.nyanband.university_organizer.service.ProjectService;
import com.nyanband.university_organizer.service.TechnologyService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final TechnologyService technologyService;
    private final SecurityUtils securityUtils;

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
    public RedirectView createProject(@RequestParam String name,
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

    @PostMapping({"/delete/{id}"})
    public RedirectView createProfile(@PathVariable Long id) {
        projectService.delete(id);
        return new RedirectView("/admin/projects");
    }

    @PostMapping({"/apply/{projectId}"})
    public RedirectView applyOnProject(@PathVariable Long projectId) {
        long userId = securityUtils.getUserId();
        projectService.applyOnProject(projectId, userId);
        return new RedirectView("/project/" + projectId);
    }

    @GetMapping({"/my"})
    public String getMyApplicationPage(Model model) {
        long userId = securityUtils.getUserId();
        model.addAttribute("projects", projectService.findAppliedProjects(userId));
        return "my_application";
    }

}
