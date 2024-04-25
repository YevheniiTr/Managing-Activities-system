package com.yevhenii.ticketingService.service;

import com.yevhenii.ticketingService.security.pojo.SignUpRequest;

public interface UserService {

    void register(SignUpRequest signUpRequest);
}
