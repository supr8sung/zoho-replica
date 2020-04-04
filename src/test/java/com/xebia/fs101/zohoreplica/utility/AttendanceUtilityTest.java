package com.xebia.fs101.zohoreplica.utility;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static com.xebia.fs101.zohoreplica.utility.AttendanceUtility.calculateDailyHours;
import static com.xebia.fs101.zohoreplica.utility.AttendanceUtility.lastCheckinTimeHours;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertEquals;
class AttendanceUtilityTest {

    @Test
    void should_calculate_total_working_hours_according_to_checkin_and_checkout() {
        LocalTime checkin = LocalTime.of(8, 4, 44);

        LocalTime checkout = LocalTime.of(11, 56, 43);

        String totalHours = calculateDailyHours(checkin, checkout);

        assertEquals(totalHours, "03:51","Here minutes are 51 and not 52 because chekout seconds are less " +
                "then checkin seconds");
    }

    @Test
    void should_give_zero_hours_if_user_have_not_checked_in_and_checked_out() {
        LocalTime checkin=LocalTime.now();
        LocalTime checkout=null;
        String totalHours = calculateDailyHours(checkin, checkout);
        assertEquals(totalHours,"00:00","You should check in and check out to get total hours");

    }

    @Test
    void should_give_last_checkin_time() {
        LocalTime localTime = LocalTime.of(23, 5,6);
        String checkinTime = lastCheckinTimeHours(localTime);
        assertEquals("23:05:06",checkinTime);
    }

    @Test
    public void test(){
        LocalTime t1 = LocalTime.of(4, 5);
        LocalTime t2 = LocalTime.of(6, 8);
        long hours = HOURS.between(t1, t2);
        long minutes = MINUTES.between(t1, t2)%60;
        System.out.println("minutes = " + minutes);
        System.out.println("hours = " + hours);

        LocalTime t3 = LocalTime.of(7, 55);
        LocalTime t4 = LocalTime.of(9, 45);
        hours+=HOURS.between(t3, t4);
        System.out.println("hours = " + hours);
        minutes+=MINUTES.between(t3,t4)%60;
        System.out.println("minutes = " + minutes);

    }
}