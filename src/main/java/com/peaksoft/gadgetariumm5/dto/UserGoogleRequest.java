package com.peaksoft.gadgetariumm5.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

@Getter
@Setter
public class UserGoogleRequest {
    private OAuth2AuthenticationToken oAuth2AuthenticationToken;
}
