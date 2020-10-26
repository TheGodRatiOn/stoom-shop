package com.stoom.demo.services;

import com.stoom.demo.requests.UserRequest;
import com.stoom.demo.entities.User;
import com.stoom.demo.repositories.UserRepository;
import com.stoom.demo.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository userRepository;

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

    public ResponseEntity<HttpStatus> createUser(UserRequest userRequest){
        if (userRequest.getUserReqRole().equals("USER") || userRequest.getUserReqRole().equals("PUBLISHER") || userRequest.getUserReqRole().equals("EMPLOYEE")){
            User user = new User(UUID.randomUUID().toString(), null, userRequest.getUserReqName(), userRequest.getUserReqRole(), null);
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
