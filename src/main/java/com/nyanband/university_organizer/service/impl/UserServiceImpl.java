package com.nyanband.university_organizer.service.impl;

import com.nyanband.university_organizer.entity.User;
import com.nyanband.university_organizer.entity.enums.ERole;
import com.nyanband.university_organizer.repository.RoleRepository;
import com.nyanband.university_organizer.repository.UserRepository;
import com.nyanband.university_organizer.security.pojo.SignUpRequest;
import com.nyanband.university_organizer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void register(SignUpRequest signUpRequest) {
        userRepository.save(new User(
                signUpRequest.getEmail(),
                signUpRequest.getPassword(),
                Collections.singletonList(roleRepository.findByName(ERole.USER).orElseThrow(
                        () -> new RuntimeException("Role USER dont found")
                ))
        ));
    }
}
