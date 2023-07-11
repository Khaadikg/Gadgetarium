package com.peaksoft.gadgetariumm5.controller;

import com.peaksoft.gadgetariumm5.config.jwt.JwtTokenUtil;
import com.peaksoft.gadgetariumm5.dto.*;
import com.peaksoft.gadgetariumm5.model.entity.User;
import com.peaksoft.gadgetariumm5.repository.UserRepository;
import com.peaksoft.gadgetariumm5.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jwt")
public class AuthController {
    private final UserService service;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final LoginMapper loginMapper;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/sign-up")
    @Operation(summary = "Sign up",description = "User can register")
    public UserResponse signUp(@RequestBody UserRequest request) throws Exception {
        return service.registration(request);
    }

    @PostMapping("sign-in")
    @Operation(summary = "Sing in",description = "User can Sign in")
    public LoginResponse signIn(@RequestBody LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword());
        authenticationManager.authenticate(token);
        User user = userRepository.findByEmail(token.getName()).get();
        return loginMapper.loginView(jwtTokenUtil.generateToken(user),"Successful",user);
    }

}
