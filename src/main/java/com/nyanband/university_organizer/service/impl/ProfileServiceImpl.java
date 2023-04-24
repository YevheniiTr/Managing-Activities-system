package com.nyanband.university_organizer.service.impl;

import com.nyanband.university_organizer.dto.ViewProfileDTO;
import com.nyanband.university_organizer.dto.SaveProfileDTO;
import com.nyanband.university_organizer.dto.mapper.ProfileMapper;
import com.nyanband.university_organizer.entity.Profile;
import com.nyanband.university_organizer.repository.ProfileRepository;
import com.nyanband.university_organizer.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ProfileServiceImpl implements ProfileService {

    ProfileRepository profileRepository;
    ProfileMapper profileMapper;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    @Override
    public List<ViewProfileDTO> findAll() {
        return profileRepository
                .findAll()
                .stream()
                .map(profileMapper::toDto)
                .collect(toList());
    }

    @Override
    public Optional<ViewProfileDTO> find(long id) {
        return profileRepository
                .findById(id)
                .map(profileMapper::toDto);
    }

    @Override
    @Transactional
    public Optional<ViewProfileDTO> findByUserId(long userId) {
        return profileRepository
                .findByUserId(userId)
                .map(profileMapper::toDto);
    }

    @Override
    @Transactional
    public ViewProfileDTO save(SaveProfileDTO saveProfileDTO) {
        Profile savedProfile = profileRepository.save(profileMapper.toEntity(saveProfileDTO));
        return profileMapper.toDto(savedProfile);
    }

    @Override
    @Transactional
    public ViewProfileDTO update(SaveProfileDTO saveProfileDTO) {
        Profile profileInitial = profileRepository.findByUserId(saveProfileDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User without profile trying to update profile"));

        Profile profileToUpdate = profileMapper.toEntity(saveProfileDTO);
        profileToUpdate.setId(profileInitial.getId());

        Profile savedProfile = profileRepository.save(profileToUpdate);
        return profileMapper.toDto(savedProfile);
    }
}
