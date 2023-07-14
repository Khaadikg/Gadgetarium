package com.peaksoft.gadgetariumm5.dto;

import com.peaksoft.gadgetariumm5.model.enums.Delivery;
import com.peaksoft.gadgetariumm5.model.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class OrderAdminResponse {
    private Status status;
    private LocalDate startDate;
    private LocalDate finishDate;
    private String firstName;
    private String lastName;
    private LocalDate hours;
    private int amount;
    private long grandTotal;
    private Delivery delivery;

}

