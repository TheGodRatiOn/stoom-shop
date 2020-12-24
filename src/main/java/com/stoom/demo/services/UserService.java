package com.stoom.demo.services;

import com.stoom.demo.configuration.AppProperties;
import com.stoom.demo.enums.Role;
import com.stoom.demo.requests.UserRequest;
import com.stoom.demo.entities.User;
import com.stoom.demo.repositories.UserRepository;
import com.stoom.demo.responses.UserResponse;
import com.stoom.demo.security.exception.TokenException;
import com.stoom.demo.security.exception.UserNotFoundException;
import com.stoom.demo.security.token.AccessTokenProvider;
import com.stoom.demo.security.token.RefreshTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;
    private final UserRepository userRepository;
    @Qualifier(value = "stoomAuthenticationManagerBean")
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<List<UserResponse>> getUsersByUserName(String userName){
        if (!userRepository.findAllByUserNameContaining(userName).isEmpty()){
            List<User> users = userRepository.findAllByUserNameContaining(userName);
            List<UserResponse> userResponses = new ArrayList<>();

            for (User user : users) {
                userResponses.add(new UserResponse(user));
            }
            return new ResponseEntity<>(userResponses, HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public void registerUser(UserRequest userRequest, HttpServletResponse httpServletResponse){
        if (userRepository.findByUserName(userRequest.getUserReqName()) == null){
            User user = new User(UUID.randomUUID().toString(), null, userRequest.getUserReqName(), passwordEncoder.encode(userRequest.getUserPassword()), Role.ROLE_CUSTOMER.toString(), null);
            userRepository.save(user);
            getAuthTokens(userRequest, httpServletResponse);

            httpServletResponse.setStatus(202);
        }else {
            httpServletResponse.setStatus(406);
        }
    }

    public void authenticateUser(UserRequest userRequest, HttpServletResponse httpServletResponse){
        if (userRepository.findByUserName(userRequest.getUserReqName()) == null){
             httpServletResponse.setStatus(400);
        } else {
            if (passwordEncoder.matches(userRequest.getUserPassword(), userRepository.findByUserName(userRequest.getUserReqName()).getUserPassword())){
                getAuthTokens(userRequest, httpServletResponse);

                httpServletResponse.addHeader("user-role", userRepository.findByUserName(userRequest.getUserReqName()).getUserRole());

                httpServletResponse.setStatus(201);
            }else {
                httpServletResponse.setStatus(403);
            }
        }
    }

    public void refreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, UserRequest userRequest) throws TokenException{
        refreshTokenProvider.validateToken(httpServletRequest);

        try {
            final Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userRequest.getUserReqName(),
                    ""
            ));

            accessTokenProvider.writeTokenToResponse(accessTokenProvider.createToken(auth), httpServletResponse);
            refreshTokenProvider.writeTokenToResponse(refreshTokenProvider.createToken(auth), httpServletResponse);
            httpServletResponse.setStatus(200);

        }catch (AuthenticationException e){
            httpServletResponse.setStatus(417);
            e.printStackTrace();
        }
    }

    private void getAuthTokens(UserRequest userRequest, HttpServletResponse httpServletResponse) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userRequest.getUserReqName(),
                    userRequest.getUserPassword()
            ));

            final String accessToken = this.accessTokenProvider.createToken(auth);
            final String refreshToken = this.refreshTokenProvider.createToken(auth);

            this.accessTokenProvider.writeTokenToResponse(accessToken, httpServletResponse);
            this.refreshTokenProvider.writeTokenToResponse(refreshToken, httpServletResponse);

        } catch (AuthenticationException e) {
            httpServletResponse.setStatus(503);
            e.printStackTrace();
        }
    }

    public ResponseEntity<UserResponse> getUserID(String userName){
        UserResponse userResponse = new UserResponse();
        if (userRepository.findByUserName(userName) != null) {
            User user = userRepository.findByUserName(userName);
            userResponse.setUserResID(user.getUserID());
            userResponse.setUserResName(user.getUserName());

            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}