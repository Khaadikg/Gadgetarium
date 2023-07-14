package com.peaksoft.gadgetariumm5.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OderAdminResponse {
    private String firstName;
    private String lastName;
    private String orderNumber;
    private int amount;
    private double grandTotal;
    private String delivery;
    private String status;

}

