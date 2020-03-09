package com.xebia.fs101.zohoreplica.service;

import com.xebia.fs101.zohoreplica.api.request.UserRequest;
import com.xebia.fs101.zohoreplica.entity.User;
import com.xebia.fs101.zohoreplica.execption.UserNotFoundException;
import com.xebia.fs101.zohoreplica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.UUID;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User save(User user){

        return userRepository.save(user);
    }

    public User findUserById(UUID id){
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User id is not valid"));
    }

    public User findUser(String username) {
        return userRepository.findByUsername(username);
    }
}
