package com.xebia.fs101.zohoreplica.controller;


import com.xebia.fs101.zohoreplica.api.request.UserRequest;
import com.xebia.fs101.zohoreplica.api.response.ZohoReplicaResponse;
import com.xebia.fs101.zohoreplica.entity.User;
import com.xebia.fs101.zohoreplica.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.xebia.fs101.zohoreplica.api.constant.ApplicationConstant.TXN_SUCCESS;
@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserRequest userRequest) {

        User savedUser = userService.save(userRequest.toUser());
        ZohoReplicaResponse zohoReplicaResponse =
                new ZohoReplicaResponse.Builder()
                        .withStatus(TXN_SUCCESS)
                        .withData(savedUser)
                        .withMessage("User saved successfully")
                        .build();
        return new ResponseEntity<>(zohoReplicaResponse, HttpStatus.CREATED);
    }

    @GetMapping("/profiles/{username}")
    public ResponseEntity<?> viewUser(@PathVariable(value = "username") String username) {
        User user=userService.findUser(username);
        ZohoReplicaResponse zohoReplicaResponse = new ZohoReplicaResponse.Builder()
                .withStatus(TXN_SUCCESS)
                .withData(user)
                .build();
        return new ResponseEntity<>(zohoReplicaResponse,HttpStatus.OK);
    }
}
