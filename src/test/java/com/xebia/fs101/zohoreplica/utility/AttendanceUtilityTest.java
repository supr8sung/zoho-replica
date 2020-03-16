package com.xebia.fs101.zohoreplica.utility;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.time.LocalTime;

import static com.xebia.fs101.zohoreplica.utility.AttendanceUtility.calculateDailyHours;
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
}