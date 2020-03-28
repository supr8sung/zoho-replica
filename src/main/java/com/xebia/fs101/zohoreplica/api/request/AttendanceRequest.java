package com.xebia.fs101.zohoreplica.api.request;

import com.xebia.fs101.zohoreplica.entity.Attendance;
import com.xebia.fs101.zohoreplica.entity.User;

import java.time.LocalDate;
import java.time.LocalTime;
public class AttendanceRequest {
    private String username;


    public String getUsername() {
        return username;
    }


    public AttendanceRequest() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AttendanceRequest(String username) {
        this.username = username;
    }

    public Attendance checkin(User user){
        return new Attendance.Builder().withUser(user)
                .withDate(LocalDate.now())
                .withCheckin(LocalTime.now())
                .build();

    }
    public Attendance checkout(User user){
        return new Attendance.Builder().withUser(user)
                .withDate(LocalDate.now())
                .withCheckout(LocalTime.now())
                .build();

    }
}
