package com.nyanband.university_organizer.service;

import com.nyanband.university_organizer.dto.SaveProfileDTO;
import com.nyanband.university_organizer.security.pojo.SignUpRequest;

public interface ProfileService {

    void save(SaveProfileDTO saveProfileDTO);
}
