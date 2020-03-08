package com.xebia.fs101.zohoreplica.controller;


import com.xebia.fs101.zohoreplica.api.constant.ApplicationConstant;
import com.xebia.fs101.zohoreplica.api.request.UserRequest;
import com.xebia.fs101.zohoreplica.api.response.ZohoReplicaResponse;
import com.xebia.fs101.zohoreplica.entity.User;
import com.xebia.fs101.zohoreplica.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserRequest userRequest) {

        User savedUser = userService.save(userRequest.toUser());
        ZohoReplicaResponse zohoReplicaResponse =
                new ZohoReplicaResponse.Builder()
                        .withStatus(ApplicationConstant.TXN_SUCCESS)
                        .withData(savedUser)
                        .withMessage("User saved successfully")
                        .build();
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
