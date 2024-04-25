package com.yevhenii.organisationSystem.services;

import com.yevhenii.organisationSystem.security.pojo.SignUpRequest;

public interface UserService {

    void register(SignUpRequest signUpRequest);
}
