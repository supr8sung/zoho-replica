package com.xebia.fs101.zohoreplica.api.request;

import com.xebia.fs101.zohoreplica.entity.Attendance;
import com.xebia.fs101.zohoreplica.entity.User;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
public class AttendanceRequest {
    private UUID userId;


    public UUID getUserId() {
        return userId;
    }


    public AttendanceRequest() {
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public AttendanceRequest(UUID userId) {
        this.userId = userId;
    }

    public Attendance checkin(User user){
        return new Attendance.Builder().withUsername(user)
                .withDate(LocalDate.now())
                .withCheckin(LocalTime.now())
                .build();

    }
    public Attendance checkout(User user){
        return new Attendance.Builder().withUsername(user)
                .withDate(LocalDate.now())
                .withCheckout(LocalTime.now())
                .build();

    }
}
