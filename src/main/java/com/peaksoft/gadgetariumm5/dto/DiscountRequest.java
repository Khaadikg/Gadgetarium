package com.peaksoft.gadgetariumm5.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DiscountRequest {
    private LocalDate dateOfStart;
    private LocalDate dateOfFinish;
    private int percentage;
}
