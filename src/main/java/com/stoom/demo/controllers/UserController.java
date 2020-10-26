package com.stoom.demo.controllers;

import com.stoom.demo.requests.UserRequest;
import com.stoom.demo.entities.User;
import com.stoom.demo.services.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Validated
@Api(value = "UserController")
public class UserController {
    private UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<List<User>> getUsersByUserName(@Valid @PathVariable(name = "userName") String userName){
        return userService.getUsersByUserName(userName);
    }

    @PostMapping("/")
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody UserRequest userRequest){
        return userService.createUser(userRequest);
    }
}
