package com.peaksoft.gadgetariumm5.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderRequest {
    private String deliveryOptions;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;

}
