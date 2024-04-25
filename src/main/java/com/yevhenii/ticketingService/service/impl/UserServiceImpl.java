package com.yevhenii.ticketingService.service.impl;

import com.yevhenii.ticketingService.entity.User;
import com.yevhenii.ticketingService.entity.enums.ERole;
import com.yevhenii.ticketingService.repository.RoleRepository;
import com.yevhenii.ticketingService.repository.UserRepository;
import com.yevhenii.ticketingService.security.pojo.SignUpRequest;
import com.yevhenii.ticketingService.service.UserService;
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
