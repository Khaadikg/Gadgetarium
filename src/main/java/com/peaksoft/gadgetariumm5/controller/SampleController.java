package com.peaksoft.gadgetariumm5.controller;

import com.peaksoft.gadgetariumm5.dto.UserGoogleResponse;
import com.peaksoft.gadgetariumm5.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class SampleController {
    private final UserService userService;

    @GetMapping("/oauth2")
    public UserGoogleResponse create(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        return userService.createAndSaveUserByGmail(oAuth2AuthenticationToken);
}}
