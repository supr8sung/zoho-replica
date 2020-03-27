package com.xebia.fs101.zohoreplica.controller;

import com.xebia.fs101.zohoreplica.api.request.AttendanceRequest;
import com.xebia.fs101.zohoreplica.api.response.ZohoReplicaResponse;
import com.xebia.fs101.zohoreplica.entity.Attendance;
import com.xebia.fs101.zohoreplica.entity.Employee;
import com.xebia.fs101.zohoreplica.service.AttendanceService;
import com.xebia.fs101.zohoreplica.service.EmployeeService;
import com.xebia.fs101.zohoreplica.service.MailService;
import com.xebia.fs101.zohoreplica.utility.MailUtility;
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
@RequestMapping(value = "/zoho")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private MailService mailService;


    @PostMapping("/checkin")
    public ResponseEntity<?> checkin(@Valid @RequestBody AttendanceRequest attendanceRequest) {

        Employee employee = employeeService.findByName(attendanceRequest.getUsername());
        attendanceService.doCheckin(attendanceRequest.checkin(employee));

        ZohoReplicaResponse zohoReplicaResponse = getResponse(TXN_SUCESS, "check in successful",
                LocalTime.now());

        return new ResponseEntity<>(zohoReplicaResponse, CREATED);
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@Valid @RequestBody AttendanceRequest attendanceRequest) {

        Employee employee = employeeService.findByName(attendanceRequest.getUsername());
        attendanceService.doCheckout(employee.getId(), LocalTime.now());

        ZohoReplicaResponse zohoReplicaResponse = getResponse(TXN_SUCESS, "check out successful",
                LocalTime.now());

        return new ResponseEntity<>(zohoReplicaResponse, CREATED);
    }

    @GetMapping("/dailyhours/{username}")
    public ResponseEntity<?> dailyHours(@PathVariable(value = "username") String username) {
        Employee employee = employeeService.findByName(username);
        Attendance attendance = attendanceService.attendanceDetails(employee);
        String totalHours = calculateDailyHours(attendance.getCheckinHours(), attendance.getCheckoutHours());
        ZohoReplicaResponse zohoReplicaResponse = getResponse(TXN_SUCESS, null, totalHours);
        return new ResponseEntity<>(zohoReplicaResponse, OK);
    }


    //this should be visible only after successful checkin and checkout
    @GetMapping("/dailyhours-mail/{username}")
    public ResponseEntity<?> sendMail(@PathVariable(value = "username") String username) {
        Employee employee = employeeService.findByName(username);
        Attendance attendance = attendanceService.attendanceDetails(employee);
        String mailBody = MailUtility.generateReportBody(employee.getFullname(),
                attendance.getCheckinHours(),attendance.getCheckoutHours());
        mailService.sendMail(employee.getEmail(), mailBody);
        ZohoReplicaResponse zohoReplicaResponse = getResponse(TXN_SUCESS, "Mail Sent successfully", null);
        return new ResponseEntity<>(zohoReplicaResponse , OK);
    }

    private ZohoReplicaResponse getResponse(String status, String message, Object data) {
        return new ZohoReplicaResponse.Builder()
                .withData(data)
                .withStatus(status)
                .withMessage(message)
                .build();
    }
}
