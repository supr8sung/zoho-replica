package com.xebia.fs101.zohoreplica.controller;

import com.xebia.fs101.zohoreplica.api.request.AttendanceRequest;
import com.xebia.fs101.zohoreplica.api.response.ZohoReplicaResponse;
import com.xebia.fs101.zohoreplica.entity.Attendance;
import com.xebia.fs101.zohoreplica.entity.Employee;
import com.xebia.fs101.zohoreplica.service.AttendanceService;
import com.xebia.fs101.zohoreplica.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.time.LocalTime;

import static com.xebia.fs101.zohoreplica.api.constant.ApplicationConstant.TXN_SUCESS;
import static com.xebia.fs101.zohoreplica.utility.AttendanceUtility.calculateDailyHours;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
@RestController
@RequestMapping(value="/zoho")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/checkin")
    public ResponseEntity<?> checkin(@Valid @RequestBody AttendanceRequest attendanceRequest) {

        Employee employee = employeeService.findByName(attendanceRequest.getUsername());
        attendanceService.doCheckin(attendanceRequest.checkin(employee));

        ZohoReplicaResponse zohoReplicaResponse = new ZohoReplicaResponse.Builder()
                .withStatus(TXN_SUCESS)
                .withMessage("Checked in successful")
                .build();

        return new ResponseEntity<>(zohoReplicaResponse, CREATED);
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@Valid @RequestBody AttendanceRequest attendanceRequest) {

        Employee employee = employeeService.findByName(attendanceRequest.getUsername());
        attendanceService.doCheckout(employee.getId(), LocalTime.now());

        ZohoReplicaResponse zohoReplicaResponse = new ZohoReplicaResponse.Builder()
                .withStatus(TXN_SUCESS)
                .withMessage("Checked out successful")
                .build();

        return new ResponseEntity<>(zohoReplicaResponse, CREATED);
    }

    @GetMapping("/dailyhours/{username}")
    public ResponseEntity<?> dailyHours(@PathVariable( value = "username") String username){
        Employee employee = employeeService.findByName(username);
        Attendance attendance = attendanceService.attendanceDetails(employee);

        String totalHours = calculateDailyHours(attendance.getCheckinHours(), attendance.getCheckoutHours());
        ZohoReplicaResponse zohoReplicaResponse=new ZohoReplicaResponse.Builder()
                .withStatus(TXN_SUCESS)
                .withData(totalHours)
                .build();
        return new ResponseEntity<>(zohoReplicaResponse,OK);
    }
}
