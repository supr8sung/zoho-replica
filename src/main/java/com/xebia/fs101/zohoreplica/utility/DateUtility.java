package com.xebia.fs101.zohoreplica.utility;


import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
public class DateUtility {


    public static LocalDate getLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("d/MM/yyyy"));
    }

    public static int calculateAge(LocalDate birthday) {
        LocalDate localDate = LocalDate.now();
        if (localDate.getMonthValue() >= birthday.getMonthValue() && localDate.getDayOfMonth() >= birthday.getDayOfMonth())
            return localDate.getYear() - birthday.getYear();
        return localDate.getYear() - birthday.getYear() - 1;
    }

}
