package com.stoom.demo.controllers;

import com.stoom.demo.requests.UserRequest;
import com.stoom.demo.responses.UserResponse;
import com.stoom.demo.services.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stoom/user")
@Validated
@Api(value = "UserController")
public class UserController {
    private UserService userService;

    @GetMapping("/{userName}")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<List<UserResponse>> getUsersByUserName(@Valid @PathVariable(name = "userName") String userName){
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
}
