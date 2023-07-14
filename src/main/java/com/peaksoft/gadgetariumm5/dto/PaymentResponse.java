package com.peaksoft.gadgetariumm5.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentResponse {
    private Long id;
    private  String payment;
    private String cardNumber;
    private String  month;
    private  String  year;

}
