package com.stoom.demo.services;

import com.stoom.demo.entities.User;
import com.stoom.demo.enums.Role;
import com.stoom.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        final User user = userRepository.findByUserName(userName);
        if(user == null) throw new UsernameNotFoundException("");
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserID())
                .password(user.getUserPassword())
                .authorities(user.getUserRole())
                .disabled(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .accountExpired(false)
                .build();
    }

    public UserDetails loadById(String userID) throws IllegalAccessError {
        final User user = userRepository.findById(userID).orElseThrow(IllegalAccessError::new);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserID())
                .password(user.getUserPassword())
                .authorities(user.getUserRole())
                .disabled(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .accountExpired(false)
                .build();
    }
}
