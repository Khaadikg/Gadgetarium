package com.peaksoft.gadgetariumm5.controller;

import com.peaksoft.gadgetariumm5.config.jwt.JwtTokenUtil;
import com.peaksoft.gadgetariumm5.dto.*;
import com.peaksoft.gadgetariumm5.model.entity.User;
import com.peaksoft.gadgetariumm5.repository.UserRepository;
import com.peaksoft.gadgetariumm5.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jwt")
@Tag(name = "Auth Api")
public class AuthController {
    private final UserService service;

    @PostMapping("/sign-up")
    public UserResponse signUp(@RequestBody UserRequest request) throws Exception {
        return service.registration(request);
    }


}
