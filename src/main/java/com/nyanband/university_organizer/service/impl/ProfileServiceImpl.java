package com.nyanband.university_organizer.service.impl;

import com.nyanband.university_organizer.dto.SaveProfileDTO;
import com.nyanband.university_organizer.dto.mapper.ProfileMapper;
import com.nyanband.university_organizer.entity.User;
import com.nyanband.university_organizer.entity.enums.ERole;
import com.nyanband.university_organizer.repository.ProfileRepository;
import com.nyanband.university_organizer.repository.RoleRepository;
import com.nyanband.university_organizer.repository.UserRepository;
import com.nyanband.university_organizer.security.pojo.SignUpRequest;
import com.nyanband.university_organizer.service.ProfileService;
import com.nyanband.university_organizer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

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
    @Transactional
    public void save(SaveProfileDTO saveProfileDTO) {
        profileRepository.save(profileMapper.toEntity(saveProfileDTO));
    }
}
