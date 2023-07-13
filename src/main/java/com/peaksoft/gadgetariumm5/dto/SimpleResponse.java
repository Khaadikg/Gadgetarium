package com.peaksoft.gadgetariumm5.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class SimpleResponse {
    private HttpStatus httpStatus;
    private String message;
}
