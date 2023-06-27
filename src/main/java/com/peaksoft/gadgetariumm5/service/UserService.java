package com.peaksoft.gadgetariumm5.service;

import com.peaksoft.gadgetariumm5.dto.UserGoogleResponse;
import com.peaksoft.gadgetariumm5.model.entity.User;
import com.peaksoft.gadgetariumm5.model.enums.Role;

import com.peaksoft.gadgetariumm5.repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserGoogleResponse createAndSaveUserByGmail(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        JSONObject json = new JSONObject(oAuth2AuthenticationToken.getPrincipal());
        User user = new User();
        user.setId(user.getId());
        user.setFirstName((String) json.get("givenName"));
        user.setLastName((String) json.get("familyName"));
        user.setEmail((String) json.get("email"));
        Object address = json.get("address");
        user.setAddress(String.valueOf(address));
        JSONArray roles = (JSONArray) json.get("authorities");
        String roleName = String.valueOf(roles.getJSONObject(0).get("authority"));
        user.setRole(Role.valueOf(roleName.substring(5)));
        user.setCreateDate(LocalDate.now());
        userRepository.save(user);
        return mapToGoogleResponse(user);

    }

    public UserGoogleResponse mapToGoogleResponse(User user) {
        return UserGoogleResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .address(user.getAddress())
                .create(LocalDate.now()).build();
    }
}
