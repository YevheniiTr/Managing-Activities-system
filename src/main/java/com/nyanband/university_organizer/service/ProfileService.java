package com.nyanband.university_organizer.service;

import com.nyanband.university_organizer.dto.ProfileDTO;
import com.nyanband.university_organizer.dto.SaveProfileDTO;

import java.util.List;
import java.util.Optional;

public interface ProfileService {

    List<ProfileDTO> findAll();

    Optional<ProfileDTO> find(long id);

    Optional<ProfileDTO> findByUserId(long userId);

    ProfileDTO save(SaveProfileDTO saveProfileDTO);

    ProfileDTO update(SaveProfileDTO saveProfileDTO);
}
