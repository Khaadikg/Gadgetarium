package com.peaksoft.gadgetariumm5.dto;

import com.peaksoft.gadgetariumm5.model.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private LocalDate localDate;
    private Role role;

}
