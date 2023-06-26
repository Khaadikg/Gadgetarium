package com.peaksoft.gadgetariumm5.controller;

import com.peaksoft.gadgetariumm5.dto.UserRequest;
import com.peaksoft.gadgetariumm5.dto.UserResponse;
import com.peaksoft.gadgetariumm5.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jwt")
public class AuthController {
    private final UserService service;


    @PostMapping("/sign-up")
    public UserResponse signUp(@RequestBody UserRequest request) throws Exception {
        return service.registration(request);
    }

}
