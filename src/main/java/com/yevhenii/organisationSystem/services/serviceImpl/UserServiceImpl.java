package com.yevhenii.organisationSystem.services.serviceImpl;

import com.yevhenii.organisationSystem.entity.User;
import com.yevhenii.organisationSystem.entity.enums.ERole;
import com.yevhenii.organisationSystem.repository.RoleRepository;
import com.yevhenii.organisationSystem.repository.UserRepository;
import com.yevhenii.organisationSystem.security.pojo.SignUpRequest;
import com.yevhenii.organisationSystem.services.UserService;
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
                Collections.singletonList(roleRepository.findByName(ERole.ROLE_USER).orElseThrow(
                        () -> new RuntimeException("Role USER dont found")
                ))
        ));
    }
}
