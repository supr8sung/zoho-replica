package com.xebia.fs101.zohoreplica.service;

import com.xebia.fs101.zohoreplica.model.Birthday;
import com.xebia.fs101.zohoreplica.api.response.LoggedInUserResponse;
import com.xebia.fs101.zohoreplica.entity.Attendance;
import com.xebia.fs101.zohoreplica.entity.User;
import com.xebia.fs101.zohoreplica.repository.AttendanceRepository;
import com.xebia.fs101.zohoreplica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

import static com.xebia.fs101.zohoreplica.api.constant.ApplicationConstant.*;
import static com.xebia.fs101.zohoreplica.utility.AttendanceUtility.getHours;
import static com.xebia.fs101.zohoreplica.utility.AttendanceUtility.getMinutes;
import static com.xebia.fs101.zohoreplica.utility.AttendanceUtility.totalHours;
@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    public Attendance checkout(Long id) {

        Attendance attendance =
                attendanceRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User is not" +
                        " " +
                        "chekcked in"));
        attendance.setCheckout(LocalTime.now());
        return attendanceRepository.save(attendance);

    }

    public Attendance attendanceDetails(User user) {
        return attendanceRepository.findByUser(user);
    }

    public Attendance checkin(User user) {
        Attendance attendance = checkinDetails(user);
        return attendanceRepository.save(attendance);
    }

    public Attendance checkinDetails(User user) {
        return new Attendance.Builder().withUser(user)
                .withDate(LocalDate.now())
                .withCheckin(LocalTime.now())
                .build();

    }

    public Attendance checkoutDetails(User user) {
        return new Attendance.Builder().withUser(user)
                .withDate(LocalDate.now())
                .withCheckout(LocalTime.now())
                .build();

    }


    public LoggedInUserResponse getAllLoginDetails(User user) {

        LocalTime lastcheckin = LocalTime.MIN;
        String totalHours = "00:00";
        Long checkinId = NOT_CHECKED_IN;
        List<Birthday> allBirthdays = userService.allBirthdays();
        List<Attendance> attendanceDetails = attendanceRepository.findAttendanceDetails(LocalDate.now(),
                user.getId());

        if (attendanceDetails.size() == 0) {
            return user.toLoggedInUserResponse(lastcheckin, totalHours, checkinId, allBirthdays);
        }
        long hours = 0;
        long minutes = 0;

        for (Attendance attendance : attendanceDetails) {

            //if user has check-in but not check-out
            if (attendance.getCheckinTime() != null && attendance.getCheckoutTime() == null) {
                checkinId = attendance.getId();
                lastcheckin = attendance.getCheckinTime();
            }

            hours += getHours(attendance.getCheckinTime(), attendance.getCheckoutTime());
            minutes += getMinutes(attendance.getCheckinTime(), attendance.getCheckoutTime());
        }
        //if at last he has both check-in and check-out
        int lastEntry = attendanceDetails.size();
        if (attendanceDetails.get(lastEntry - 1).getCheckinTime() != null
                && attendanceDetails.get(lastEntry - 1).getCheckoutTime() != null) {
            checkinId = ALREADY_CHECKED_OUT;
            lastcheckin = LocalTime.MIN;
        }
        totalHours = totalHours(hours, minutes);

        return user.toLoggedInUserResponse(lastcheckin, totalHours, checkinId, allBirthdays);

    }
}
