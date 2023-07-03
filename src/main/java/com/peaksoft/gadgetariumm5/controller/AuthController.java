package com.peaksoft.gadgetariumm5.controller;

import com.peaksoft.gadgetariumm5.dto.UserRequest;
import com.peaksoft.gadgetariumm5.dto.UserResponse;
import com.peaksoft.gadgetariumm5.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final LoginMapper loginMapper;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/sign-up")
    public UserResponse signUp(@RequestBody UserRequest request) throws Exception {
        return service.registration(request);
    }

    @PostMapping("/s")
    @Operation(summary = "Sing in", description = "User can Sign in")
    public LoginResponse signIn(@RequestBody LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword());

        System.out.println(token);
        //authenticationManager.authenticate(token);
        System.out.println("I'm here");
        User user = userRepository.findByEmail(token.getName()).get();
        System.out.println("It's END!!!");
        return loginMapper.loginView(jwtTokenUtil.generateToken(user), "Successful", user);
    }

}
