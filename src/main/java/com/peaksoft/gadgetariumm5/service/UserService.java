package com.peaksoft.gadgetariumm5.service;

import com.peaksoft.gadgetariumm5.config.jwt.JwtTokenUtil;
import com.peaksoft.gadgetariumm5.dto.UserGoogleResponse;
import com.peaksoft.gadgetariumm5.dto.UserRequest;
import com.peaksoft.gadgetariumm5.dto.UserResponse;
import com.peaksoft.gadgetariumm5.model.entity.Basket;
import com.peaksoft.gadgetariumm5.model.entity.User;
import com.peaksoft.gadgetariumm5.model.enums.Role;

import com.peaksoft.gadgetariumm5.repository.BasketRepository;
import com.peaksoft.gadgetariumm5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private BasketRepository basketRepository;
    private final BCryptPasswordEncoder encoder;

    private final JwtTokenUtil util;

    public UserResponse registration(UserRequest request) throws Exception {
        User user = new User();
        Basket basket = new Basket();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail());
        if (request.getConfirmation().equals(request.getPassword())) {
            user.setPassword(encoder.encode(request.getPassword()));

        } else {
            throw new Exception("Password again!");
        }
        user.setRole(Role.USER);
        user.setCreateDate(LocalDate.now());
        user.setBasket(basket);
        user.setBasketId(basket.getId());
        basket.setUser(user);
        userRepository.save(user);
        return mapToUserResponse(user);
    }


    public UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .localDate(user.getCreateDate())
                .role(user.getRole())
                .build();
    }

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
        user.setRole(Role.valueOf(roleName.replace("ROLE_", "")));
        user.setCreateDate(LocalDate.now());

        userRepository.save(user);

        String token = util.generateToken(user);

        return mapToGoogleResponse(user,token);
    }

    public UserGoogleResponse mapToGoogleResponse(User user,String token) {
        return UserGoogleResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .address(user.getAddress())
                .token(token)
                .create(LocalDate.now()).build();
    }
}
