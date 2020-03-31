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
import java.util.UUID;

import static com.xebia.fs101.zohoreplica.api.constant.QueryConstant.CHECKOUT;
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {

    Attendance findByUser(User user);

    @Transactional
    @Modifying
    @Query(CHECKOUT)
    void checkout(@Param("userId") UUID userId, @Param("date") LocalDate date,
                        @Param("time") LocalTime time);
}
