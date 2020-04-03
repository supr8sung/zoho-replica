package com.xebia.fs101.zohoreplica.repository;

import com.xebia.fs101.zohoreplica.entity.Attendance;
import com.xebia.fs101.zohoreplica.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static com.xebia.fs101.zohoreplica.api.constant.QueryConstant.CHECKOUT;
import static com.xebia.fs101.zohoreplica.api.constant.QueryConstant.CHECKOUT2;
@Repository
@Transactional
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {

    Attendance findByUser(User user);


    @Modifying
    @Query( nativeQuery = true,value = "SELECT * FROM ATTENDANCE  as a WHERE a.date: date AND a.user_id=: " +
            "userId")
    List<Attendance> findAttendanceDetails(@Param("date") LocalDate date, @Param("user_id") UUID userdId);


}
