package com.peaksoft.gadgetariumm5.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DiscountResponse {
    private Long id;
    private LocalDate dateOfStart;
    private LocalDate dateOfFinish;
    private int percentage;
}
