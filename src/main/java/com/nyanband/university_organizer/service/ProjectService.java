package com.nyanband.university_organizer.service;

import com.nyanband.university_organizer.dto.SaveProjectDTO;
import com.nyanband.university_organizer.dto.UpdateProjectDTO;
import com.nyanband.university_organizer.dto.ViewProfileDTO;
import com.nyanband.university_organizer.dto.SaveProfileDTO;
import com.nyanband.university_organizer.dto.ViewProjectDTO;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    List<ViewProjectDTO> findAll();

    List<ViewProjectDTO> findAppliedProjects(long userId);

    Optional<ViewProjectDTO> find(long id);

    ViewProjectDTO save(SaveProjectDTO projectDTO);

    ViewProjectDTO update(UpdateProjectDTO projectDTO);

    void applyOnProject(long projectId, long userId);

    void delete(long id);
}
