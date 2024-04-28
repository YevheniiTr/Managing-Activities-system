package com.yevhenii.organisationSystem.dto;

import lombok.Value;

import javax.persistence.Column;
@Value
public class ProfileDTO {
    String  organisation;
    String firstname;
    String surname;
    String phone;
}
