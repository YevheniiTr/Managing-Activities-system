package com.nyanband.university_organizer.service.impl;

import com.nyanband.university_organizer.dto.SaveProjectDTO;
import com.nyanband.university_organizer.dto.UpdateProjectDTO;
import com.nyanband.university_organizer.dto.ViewProjectDTO;
import com.nyanband.university_organizer.dto.mapper.ProjectMapper;
import com.nyanband.university_organizer.entity.Profile;
import com.nyanband.university_organizer.entity.Project;
import com.nyanband.university_organizer.repository.ProfileRepository;
import com.nyanband.university_organizer.repository.ProjectRepository;
import com.nyanband.university_organizer.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProfileRepository profileRepository;
    private final ProjectMapper projectMapper;


    @Override
    public List<ViewProjectDTO> findAll() {
        return projectRepository
                .findAll()
                .stream()
                .map(projectMapper::toDto)
                .collect(toList());
    }

    @Override
    public List<ViewProjectDTO> findAppliedProjects(long userId) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return profile.getAppliedProjects().stream()
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

    @Override
    public void applyOnProject(long projectId, long userId) {
        Profile profile = profileRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Profile does not exist"));
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project does not exist"));
        project.getCandidates().add(profile);
        projectRepository.save(project);
    }

    @Override
    public void delete(long id) {
        projectRepository.deleteById(id);
    }
}
