package com.xebia.fs101.zohoreplica.utility;

import java.time.LocalTime;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;

public class AttendanceUtility {
    public static String calculateDailyHours(LocalTime checkin, LocalTime checkout) {
        if (Objects.isNull(checkin) || Objects.isNull(checkout))
            return "00:00";
        long totalHours = HOURS.between(checkin, checkout);
        long totalMinutes = MINUTES.between(checkin, checkout) % 60;   //converting into minutes in one hour

        StringBuilder totalTime = new StringBuilder();
        return totalTime.append(totalHours < 10 ? "0" + totalHours : totalHours)
                .append(":")
                .append(totalMinutes < 10 ? "0" + totalMinutes : totalMinutes).toString();

    }

    public static String lastCheckinTimeHours(LocalTime checkinTime) {

        return (checkinTime.getHour() < 10 ? "0" + checkinTime.getHour() : checkinTime.getHour()) +
                ":" +
                (checkinTime.getMinute() < 10 ? "0" + checkinTime.getMinute() : checkinTime.getMinute()) +
                ":" +
                (checkinTime.getSecond() < 10 ? "0" + checkinTime.getSecond() : checkinTime.getSecond());
    }


    public static long getHours(LocalTime checkinTime, LocalTime checkoutTime) {
        if (checkoutTime == null || checkinTime == null)
            return 0;
        return HOURS.between(checkinTime, checkoutTime);
    }

    public static long getMinutes(LocalTime checkinTime, LocalTime checkoutTime) {
        if (checkoutTime == null || checkinTime == null)
            return 0;
        return MINUTES.between(checkinTime, checkoutTime);
    }

    public static String totalHours(long hours, long minutes) {

        minutes = minutes > 60 ? minutes % 60 : minutes;
        return (hours < 10 ? "0" + hours : hours)
                + ":"
                + (minutes < 10 ? "0" + minutes : minutes);
    }
}
