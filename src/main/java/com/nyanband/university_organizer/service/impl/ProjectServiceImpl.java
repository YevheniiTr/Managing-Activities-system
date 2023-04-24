package com.nyanband.university_organizer.service.impl;

import com.nyanband.university_organizer.dto.SaveProfileDTO;
import com.nyanband.university_organizer.dto.SaveProjectDTO;
import com.nyanband.university_organizer.dto.UpdateProjectDTO;
import com.nyanband.university_organizer.dto.ViewProfileDTO;
import com.nyanband.university_organizer.dto.ViewProjectDTO;
import com.nyanband.university_organizer.dto.mapper.ProfileMapper;
import com.nyanband.university_organizer.dto.mapper.ProjectMapper;
import com.nyanband.university_organizer.entity.Profile;
import com.nyanband.university_organizer.entity.Project;
import com.nyanband.university_organizer.repository.ProfileRepository;
import com.nyanband.university_organizer.repository.ProjectRepository;
import com.nyanband.university_organizer.service.ProfileService;
import com.nyanband.university_organizer.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final ProfileService profileService;

    public ProjectServiceImpl(ProjectRepository projectRepository,
                              ProjectMapper projectMapper,
                              ProfileService profileService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.profileService = profileService;
    }

    @Override
    public List<ViewProjectDTO> findAll() {
        return projectRepository
                .findAll()
                .stream()
                .map(projectMapper::toDto)
                .collect(toList());
    }

    @Override
    public Optional<ViewProjectDTO> find(long id) {
        return projectRepository
                .findById(id)
                .map(projectMapper::toDto);
    }

    @Override
    @Transactional
    public ViewProjectDTO save(SaveProjectDTO projectDTO) {
        Project project = projectRepository.save(projectMapper.toEntity(projectDTO));
        return projectMapper.toDto(project);
    }

    @Override
    @Transactional
    public ViewProjectDTO update(UpdateProjectDTO projectDTO) {
        Project project = projectRepository.findById(projectDTO.getId())
                .orElseThrow(() -> new RuntimeException("Project with this id dont found"));

        project.setName(project.getName());
        project.setDescription(project.getDescription());

        return projectMapper.toDto(projectRepository.save(project));
    }
}
