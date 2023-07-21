package com.peaksoft.gadgetariumm5.dto;

import com.peaksoft.gadgetariumm5.model.enums.Delivery;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class OrderResponse {
    private Long id;
    private Delivery delivery;
    private String fistName;
    private String lastName;
    private String email;
    private LocalDateTime created;
    private LocalDateTime update;
    private String address;
}
