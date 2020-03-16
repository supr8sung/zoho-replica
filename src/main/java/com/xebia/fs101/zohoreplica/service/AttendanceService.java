package com.xebia.fs101.zohoreplica.service;

import com.xebia.fs101.zohoreplica.entity.Attendance;
import com.xebia.fs101.zohoreplica.entity.Employee;
import com.xebia.fs101.zohoreplica.repository.AttendanceRepository;
import com.xebia.fs101.zohoreplica.repository.EmployeeRepository;
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
    private EmployeeRepository employeeRepository;

    public void doCheckout(UUID employeeId, LocalTime checkoutTime) {
        attendanceRepository.checkout(employeeId, LocalDate.now(), checkoutTime);

    }

    public Attendance attendanceDetails(Employee employee) {

        return attendanceRepository.findByEmployee(employee);
    }

    public void doCheckin(Attendance attendance) {
         attendanceRepository.save(attendance);
    }
}
