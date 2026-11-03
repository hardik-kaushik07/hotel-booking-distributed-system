package com.ms.gateway.controller;

import com.ms.gateway.entity.AuthResponse;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/oauth")
public class AuthController {

    private static final Logger logger  = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/login")
    public ResponseEntity<AuthResponse> login(@RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client
                                          , @AuthenticationPrincipal OidcUser user, Model model){

        logger.info("user email is: " + user.getEmail());

        AuthResponse authResponse = new AuthResponse();

        authResponse.setAccess_token(client.getAccessToken().getTokenValue());
        authResponse.setUser_id(user.getEmail());
        if(client.getRefreshToken() != null) {
            authResponse.setRefresh_token(client.getRefreshToken().getTokenValue());
        }
        if(client.getAccessToken().getExpiresAt() != null) {
            authResponse.setExpiry_date(client.getAccessToken().getExpiresAt().getEpochSecond());
        }
        List<String> authorities = user.getAuthorities().stream().map(grantedAuthority ->
            grantedAuthority.getAuthority()
        ).collect(Collectors.toList());
        authResponse.setAuthories(authorities);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
