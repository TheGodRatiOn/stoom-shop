package com.stoom.demo.responses;

import com.stoom.demo.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private String userResID;
    private String userResName;
    private String userResRole;

    public UserResponse(User user){
        this.setUserResID(user.getUserID());
        this.setUserResName(user.getUserName());
        this.setUserResRole(user.getUserRole());
    }
}
