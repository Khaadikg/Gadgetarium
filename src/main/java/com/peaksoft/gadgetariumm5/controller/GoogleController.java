package com.peaksoft.gadgetariumm5.controller;

import com.peaksoft.gadgetariumm5.dto.UserGoogleResponse;
import com.peaksoft.gadgetariumm5.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.TableGenerator;

@RestController
@RequestMapping("/oauth")
@AllArgsConstructor
@Tag(name = "Sign-up  with google. ")

public class GoogleController {
    private final UserService userService;

    @GetMapping("/registration")
    @Operation(summary = "sign-up with google.",description ="User can sign-up with google.")
    public UserGoogleResponse create(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        return userService.createAndSaveUserByGmail(oAuth2AuthenticationToken);
    }
}
