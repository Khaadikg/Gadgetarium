package com.peaksoft.gadgetariumm5.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;

@Getter
@Setter
@Builder
public class UserRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    @Transient
    private String confirmation;
}
