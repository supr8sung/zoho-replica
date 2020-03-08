package com.xebia.fs101.zohoreplica.controller;

import com.xebia.fs101.zohoreplica.api.response.ZohoReplicaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;
@RestController
public class AttendanceController {

    @Autowired
    @PostMapping("/checkin")
    public ResponseEntity<?> checkin(){
//        ZohoReplicaResponse zohoReplicaResponse=new ZohoReplicaResponse.Builder()
//                .withStatus()

        return  new ResponseEntity<>("", CREATED);
    }
}
