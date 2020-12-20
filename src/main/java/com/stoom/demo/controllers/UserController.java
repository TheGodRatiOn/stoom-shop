package com.stoom.demo.controllers;

import com.stoom.demo.requests.UserRequest;
import com.stoom.demo.responses.UserResponse;
import com.stoom.demo.security.exception.TokenException;
import com.stoom.demo.services.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/stoom/user")
@Validated
@Api(value = "UserController")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    @PreAuthorize(value = "hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<List<UserResponse>> getUsersByUserName(@Valid @RequestParam String userName){
        return userService.getUsersByUserName(userName);
    }

    @PostMapping("/registerUser")
    public void registerUser(@Valid @RequestBody UserRequest userRequest, HttpServletResponse httpServletResponse){
        userService.registerUser(userRequest, httpServletResponse);
    }

    @PostMapping("/authUser")
    public void authenticateUser(@Valid @RequestBody UserRequest userRequest, HttpServletResponse httpServletResponse){
        userService.authenticateUser(userRequest, httpServletResponse);
    }

    @PostMapping("/refreshToken")
    public void refreshToken(@Valid @RequestBody UserRequest userRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        try {
            userService.refreshToken(httpServletRequest, httpServletResponse, userRequest);
        }catch (TokenException e){
            e.printStackTrace();
            httpServletResponse.setStatus(405);
        }

    }

    @GetMapping("/getUserID")
    @PreAuthorize(value = "hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_PUBLISHER')")
    public ResponseEntity<UserResponse> getUserID(@Valid @RequestParam String userName){
        return userService.getUserID(userName);
    }
}
