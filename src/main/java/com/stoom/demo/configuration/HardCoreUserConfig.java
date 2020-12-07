package com.stoom.demo.configuration;

import com.stoom.demo.entities.User;
import com.stoom.demo.enums.Role;
import com.stoom.demo.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class HardCoreUserConfig {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public HardCoreUserConfig(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @PostConstruct
    void init(){
        User user1 = new User("24979136-39eb-4b09-acd7-a0765cc7f90f", null, "user1", passwordEncoder.encode("0001"), Role.ROLE_ADMIN.toString(), null);
        User user2 = new User("b5a9f645-00dd-4230-8f44-835770d25527", null, "user2", passwordEncoder.encode("0002"), Role.ROLE_PUBLISHER.toString(), null);
        User user3 = new User("793cf14b-a2c8-4fff-910d-1fbca4e09127", null, "user3", passwordEncoder.encode("0003"), Role.ROLE_EMPLOYEE.toString(), null);
        User user4 = new User("e2614806-b55d-4270-b03a-05a6f15141a8", null, "user4", passwordEncoder.encode("0004"), Role.ROLE_CUSTOMER.toString(), null);
        User user5 = new User("29fe5e85-799f-4097-8894-71d699d0009c", null, "user5", passwordEncoder.encode("0005"), Role.ROLE_CUSTOMER.toString(), null);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);

        for (User u:users) {
            if (userRepository.findByUserName(u.getUserName()) == null){
                userRepository.save(u);
            }
        };
    }
}
