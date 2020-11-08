package com.stoom.demo.services;

import com.stoom.demo.requests.UserRequest;
import com.stoom.demo.security.token.AccessTokenProvider;
import com.stoom.demo.security.token.RefreshTokenProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class WebAuthService {

    private final AuthenticationManager authenticationManager;

    private final AccessTokenProvider accessTokenProvider;

    private final RefreshTokenProvider refreshTokenProvider;

    public WebAuthService(@Qualifier("stoomAuthenticationManagerBean") AuthenticationManager authenticationManager,
                          AccessTokenProvider accessTokenProvider,
                          RefreshTokenProvider refreshTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.accessTokenProvider = accessTokenProvider;
        this.refreshTokenProvider = refreshTokenProvider;
    }

    public void login(UserRequest userRequest, HttpServletResponse httpServletResponse) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userRequest.getUserReqName(),
                userRequest.getUserPassword()
        ));
        final String accessToken = accessTokenProvider.createToken(auth);
        final String refreshToken = refreshTokenProvider.createToken(auth);
        accessTokenProvider.writeTokenToResponse(accessToken, httpServletResponse);
        refreshTokenProvider.writeTokenToResponse(refreshToken, httpServletResponse);

    }
}
