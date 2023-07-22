package com.peaksoft.gadgetariumm5.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderOverviewResponse {
    private double total;
    private String delivery;
    private String payment;

}
