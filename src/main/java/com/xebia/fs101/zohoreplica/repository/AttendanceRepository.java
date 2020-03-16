package com.xebia.fs101.zohoreplica.repository;

import com.xebia.fs101.zohoreplica.entity.Attendance;
import com.xebia.fs101.zohoreplica.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {

    public Attendance findByEmployee(Employee employee);

    @Transactional
    @Modifying
    @Query("UPDATE  Attendance  set checkout=:time where employee_id=:employeeId and date=:date")
    void checkout(@Param("employeeId") UUID employeeId, @Param("date") LocalDate date,
                    @Param("time")LocalTime time);
}
