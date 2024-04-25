package com.yevhenii.ticketingService.security.pojo;

import com.yevhenii.ticketingService.validation.annotation.UniqueUserEmailConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Getter
@Setter
public class SignUpRequest {
    @Email
    @UniqueUserEmailConstraint
    String email;

    @NotEmpty
    @Length(min = 5, max = 30, message = "Wrong password size (min : 5, max : 30)")
    String password;
}
