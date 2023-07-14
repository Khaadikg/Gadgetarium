package com.peaksoft.gadgetariumm5.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private String paymentMethod;
    private String cardNumber;
    private String month;
    private String year;
    private String cvc;
}
