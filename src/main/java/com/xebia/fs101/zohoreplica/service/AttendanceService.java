package com.xebia.fs101.zohoreplica.service;

import com.xebia.fs101.zohoreplica.entity.Attendance;
import com.xebia.fs101.zohoreplica.entity.User;
import com.xebia.fs101.zohoreplica.repository.AttendanceRepository;
import com.xebia.fs101.zohoreplica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    public void doCheckout(UUID employeeId, LocalTime checkoutTime) {
        attendanceRepository.checkout(employeeId, LocalDate.now(), checkoutTime);

    }

    public Attendance attendanceDetails(User user) {

        return attendanceRepository.findByUser(user);
    }

    public void doCheckin(Attendance attendance) {
         attendanceRepository.save(attendance);
    }
}
