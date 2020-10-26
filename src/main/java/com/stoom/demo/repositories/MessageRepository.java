package com.stoom.demo.repositories;

import com.stoom.demo.entities.Message;
import com.stoom.demo.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, String> {
    List<Message> findAllByMessageSenderUser(User user);
    List<Message> findAllByMessageReceiverUser(String userID);
}
