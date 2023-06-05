package com.nyanband.university_organizer.service.impl;

import com.nyanband.university_organizer.dto.ViewProfileDTO;
import com.nyanband.university_organizer.dto.SaveProfileDTO;
import com.nyanband.university_organizer.dto.mapper.ProfileMapper;
import com.nyanband.university_organizer.entity.Profile;
import com.nyanband.university_organizer.entity.Role;
import com.nyanband.university_organizer.entity.User;
import com.nyanband.university_organizer.entity.enums.ERole;
import com.nyanband.university_organizer.repository.ProfileRepository;
import com.nyanband.university_organizer.repository.RoleRepository;
import com.nyanband.university_organizer.repository.UserRepository;
import com.nyanband.university_organizer.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProfileMapper profileMapper;

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
        Profile profile = profileMapper.toEntity(saveProfileDTO);
        profile.setIsBanned(false);
        Profile savedProfile = profileRepository.save(profile);
        return profileMapper.toDto(savedProfile);
    }

    @Override
    @Transactional
    public void banProfile(long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        profile.setIsBanned(true);
    }

    @Override
    @Transactional
    public void makeAdmin(long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        User user = userRepository.findById(profile.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.getRoles().add(roleRepository.findByName(ERole.ADMIN).get());
        userRepository.save(user);
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
