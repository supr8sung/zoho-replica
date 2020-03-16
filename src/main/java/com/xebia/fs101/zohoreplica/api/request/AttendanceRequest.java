package com.xebia.fs101.zohoreplica.api.request;

import com.xebia.fs101.zohoreplica.entity.Attendance;
import com.xebia.fs101.zohoreplica.entity.Employee;

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

    public Attendance checkin(Employee employee){
        return new Attendance.Builder().withEmployee(employee)
                .withDate(LocalDate.now())
                .withCheckin(LocalTime.now())
                .build();

    }
    public Attendance checkout(Employee employee){
        return new Attendance.Builder().withEmployee(employee)
                .withDate(LocalDate.now())
                .withCheckout(LocalTime.now())
                .build();

    }
}
