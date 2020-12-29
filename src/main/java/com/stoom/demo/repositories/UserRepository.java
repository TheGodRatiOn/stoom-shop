package com.stoom.demo.repositories;

import com.stoom.demo.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    List<User> findAllByUserNameContaining(String userName);
    User findByUserName(String username);
    List<User> findByUserRole(String userRole);
}
