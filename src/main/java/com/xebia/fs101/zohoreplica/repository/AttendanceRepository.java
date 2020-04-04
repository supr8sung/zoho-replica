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
@Repository
@Transactional
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Attendance findByUser(User user);


    @Modifying
    @Query(nativeQuery = true, value = "SELECT * FROM ATTENDANCE a  WHERE a.date= ?1 AND " +
            "a.user_id= ?2")
    List<Attendance> findAttendanceDetails(LocalDate date, UUID userId);




}
