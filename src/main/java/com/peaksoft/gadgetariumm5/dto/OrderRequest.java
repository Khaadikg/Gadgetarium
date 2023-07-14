package com.peaksoft.gadgetariumm5.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private String deliveryOptions;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;

}
