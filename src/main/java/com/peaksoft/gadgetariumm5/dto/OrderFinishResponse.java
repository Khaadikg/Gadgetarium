package com.peaksoft.gadgetariumm5.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class OrderFinishResponse {
    private String applicationNumber;
    private LocalDate localDate;
    private String email;
    private String status;


}
