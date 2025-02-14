package com.yevhenii.organisationSystem.payload.request;


import javax.validation.constraints.*;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class SignupRequest {


    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 5, max = 40)
    private String password;



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}