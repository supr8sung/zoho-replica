package com.xebia.fs101.zohoreplica.service;

import com.xebia.fs101.zohoreplica.api.request.UserRequest;
import com.xebia.fs101.zohoreplica.entity.User;
import com.xebia.fs101.zohoreplica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User save(@Valid UserRequest userRequest){

        return userRepository.save(userRequest.toUser());
    }
}
