package com.peaksoft.gadgetariumm5.controller;

import com.peaksoft.gadgetariumm5.dto.UserGoogleResponse;
import com.peaksoft.gadgetariumm5.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/o")
@AllArgsConstructor
public class SampleController {
    private final UserService userService;

    @GetMapping("/oauth2")
    public UserGoogleResponse create(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        return userService.createAndSaveUserByGmail(oAuth2AuthenticationToken);


//    @GetMapping("/oauth2")
//    public Object currentUser(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
//        JSONObject json = new JSONObject(oAuth2AuthenticationToken.getPrincipal());
//
//        System.out.println();
//
//        System.out.println("HELLO WORLD");
//        //System.out.println(oAuth2AuthenticationToken.getPrincipal());
//       // User user = (User) oAuth2AuthenticationToken.getPrincipal();
//
//        return oAuth2AuthenticationToken.getPrincipal().getAttributes();
//
//    }


}}
