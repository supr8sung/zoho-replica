package com.xebia.fs101.zohoreplica.controller;

import com.xebia.fs101.zohoreplica.api.constant.ApplicationConstant;
import com.xebia.fs101.zohoreplica.api.request.AttendanceRequest;
import com.xebia.fs101.zohoreplica.api.response.ZohoReplicaResponse;
import com.xebia.fs101.zohoreplica.entity.Attendance;
import com.xebia.fs101.zohoreplica.entity.User;
import com.xebia.fs101.zohoreplica.service.AttendanceService;
import com.xebia.fs101.zohoreplica.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
@RestController
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private UserService userService;

    @PostMapping("/checkin")
    public ResponseEntity<?> checkin(@Valid @RequestBody AttendanceRequest attendanceRequest) {

        User user = userService.findUserById(attendanceRequest.getUserId());
        Attendance checkinDetails = attendanceService.save(attendanceRequest.checkin(user));

        ZohoReplicaResponse zohoReplicaResponse = new ZohoReplicaResponse.Builder()
                .withStatus(ApplicationConstant.TXN_SUCESS)
                .withMessage("Checked in successful")
                .withData(checkinDetails)
                .build();

        return new ResponseEntity<>(zohoReplicaResponse, CREATED);
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@Valid @RequestBody AttendanceRequest attendanceRequest) {

        User user = userService.findUserById(attendanceRequest.getUserId());
        Attendance checkoutDetails = attendanceService.save(attendanceRequest.checkout(user));

        ZohoReplicaResponse zohoReplicaResponse = new ZohoReplicaResponse.Builder()
                .withStatus(ApplicationConstant.TXN_SUCESS)
                .withMessage("Checked in successful")
                .withData(checkoutDetails)
                .build();

        return new ResponseEntity<>(zohoReplicaResponse, CREATED);
    }
}
