package com.xebia.fs101.zohoreplica.controller;

import com.xebia.fs101.zohoreplica.api.response.ZohoReplicaResponse;
import com.xebia.fs101.zohoreplica.entity.Attendance;
import com.xebia.fs101.zohoreplica.entity.User;
import com.xebia.fs101.zohoreplica.service.AttendanceService;
import com.xebia.fs101.zohoreplica.service.MailService;
import com.xebia.fs101.zohoreplica.service.UserService;
import com.xebia.fs101.zohoreplica.utility.AttendanceUtility;
import com.xebia.fs101.zohoreplica.utility.MailUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

import static com.xebia.fs101.zohoreplica.api.constant.ApplicationConstant.TXN_SUCESS;
import static com.xebia.fs101.zohoreplica.utility.AttendanceUtility.calculateDailyHours;
import static com.xebia.fs101.zohoreplica.utility.AttendanceUtility.lastCheckinTime;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
@RestController
@RequestMapping(value = "/zoho")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;


    @PostMapping("/user/checkin")
    public ResponseEntity<?> checkin() {

        User user = getLoggedInUser();
        Attendance attendance = attendanceService.checkin(user);
        ZohoReplicaResponse zohoReplicaResponse = getResponse(TXN_SUCESS,
                "You checked in at " + lastCheckinTime(attendance.checkinTime()),
                attendance.getId());
        return new ResponseEntity<>(zohoReplicaResponse, CREATED);
    }

    @PostMapping("/user/checkout/{id}")
    public ResponseEntity<?> checkout(@PathVariable(value = "id") Long id) {

        Attendance attendance = attendanceService.checkout(id);
        String totalHours = calculateDailyHours(attendance.checkinTime(), attendance.checkoutTime());
        ZohoReplicaResponse zohoReplicaResponse = getResponse(TXN_SUCESS, "Your total hours are ",
                totalHours);
        return new ResponseEntity<>(zohoReplicaResponse, CREATED);
    }



    @GetMapping("/user/dailyhours")
    public ResponseEntity<?> dailyHours() {
        User user = getLoggedInUser();
        Attendance attendance = attendanceService.attendanceDetails(user);
        String totalHours = calculateDailyHours(attendance.checkinTime(), attendance.checkoutTime());
        ZohoReplicaResponse zohoReplicaResponse = getResponse(TXN_SUCESS, "Daily hours", totalHours);
        return new ResponseEntity<>(zohoReplicaResponse, OK);
    }


    //this should be visible only after successful checkin and checkout
    @GetMapping("/user/dailyhours-report")
    public ResponseEntity<?> sendMail() {
        User user = getLoggedInUser();
        Attendance attendance = attendanceService.attendanceDetails(user);
        String mailBody = MailUtility.generateReportBody(user.getFullname(),
                attendance.checkinTime(), attendance.checkoutTime());
        mailService.sendMail(user.getEmail(), mailBody);
        ZohoReplicaResponse zohoReplicaResponse = getResponse(TXN_SUCESS, "Mail Sent successfully", null);
        return new ResponseEntity<>(zohoReplicaResponse, OK);
    }

    private ZohoReplicaResponse getResponse(String status, String message, Object data) {
        return new ZohoReplicaResponse.Builder()
                .withData(data)
                .withStatus(status)
                .withMessage(message)
                .build();
    }

    private User getLoggedInUser() {
        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userService.findByName(username);
        if (user == null)
            throw new UsernameNotFoundException("No logged in user found");
        return user;
    }

}
