package com.nyanband.university_organizer.service;

import com.nyanband.university_organizer.dto.ViewProfileDTO;
import com.nyanband.university_organizer.dto.SaveProfileDTO;

import java.util.List;
import java.util.Optional;

public interface ProfileService {

    List<ViewProfileDTO> findAll();

    Optional<ViewProfileDTO> find(long id);

    Optional<ViewProfileDTO> findByUserId(long userId);

    ViewProfileDTO save(SaveProfileDTO saveProfileDTO);

    ViewProfileDTO update(SaveProfileDTO saveProfileDTO);
}
