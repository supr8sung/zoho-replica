package com.xebia.fs101.zohoreplica.repository;

import com.xebia.fs101.zohoreplica.entity.Attendance;
import com.xebia.fs101.zohoreplica.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.xebia.fs101.zohoreplica.api.constant.QueryConstant.ATTENDANCE_DETAILS_QUERY;
@Repository
@Transactional
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Attendance findByUser(User user);


    @Modifying
    @Query(nativeQuery = true, value = ATTENDANCE_DETAILS_QUERY)
    List<Attendance> findAttendanceDetails(LocalDate date, Long userId);




}
