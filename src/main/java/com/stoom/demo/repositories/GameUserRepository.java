package com.stoom.demo.repositories;

import com.stoom.demo.entities.GameUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameUserRepository extends CrudRepository<GameUser, String>{
    List<GameUser> findAllByGuUserID(String userID);

}
