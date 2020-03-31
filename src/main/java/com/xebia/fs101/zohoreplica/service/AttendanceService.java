package com.xebia.fs101.zohoreplica.service;

import com.xebia.fs101.zohoreplica.entity.Attendance;
import com.xebia.fs101.zohoreplica.entity.User;
import com.xebia.fs101.zohoreplica.repository.AttendanceRepository;
import com.xebia.fs101.zohoreplica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;
@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;




    public void checkout(User user) {
         attendanceRepository.checkout(user.getId(),LocalDate.now(),LocalTime.now());
    }

    public Attendance attendanceDetails(User user) {

        return attendanceRepository.findByUser(user);
    }

    public Attendance checkin(User user) {
        Attendance attendance = checkinDetails(user);
        return attendanceRepository.save(attendance);
    }
    public Attendance checkinDetails(User user){
        return new Attendance.Builder().withUser(user)
                .withDate(LocalDate.now())
                .withCheckin(LocalTime.now())
                .build();

    }

    public Attendance checkoutDetails(User user){
        return new Attendance.Builder().withUser(user)
                .withDate(LocalDate.now())
                .withCheckout(LocalTime.now())
                .build();

    }
}
