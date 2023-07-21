package com.peaksoft.gadgetariumm5.model.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum Status {
    PENDING,
    IN_PROCESSING,
    READY_TO_PICK_UP,
    RECEIVED,
    COURIER_ON_THE_ROAD,
    DELIVERED,
    CANCEL
}
