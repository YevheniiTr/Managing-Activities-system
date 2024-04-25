package com.yevhenii.ticketingService.validation.validator;

import com.yevhenii.ticketingService.repository.UserRepository;
import com.yevhenii.ticketingService.validation.annotation.UniqueUserEmailConstraint;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUserEmailValidator implements
        ConstraintValidator<UniqueUserEmailConstraint, String> {

    UserRepository userRepository;

    @Autowired
    public UniqueUserEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UniqueUserEmailConstraint contactNumber) {
    }

    @Override
    public boolean isValid(String email,
                           ConstraintValidatorContext cxt) {
        return userRepository.findByEmail(email).isEmpty();
    }

}
