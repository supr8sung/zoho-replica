package com.xebia.fs101.zohoreplica.utility;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class DateUtility {


    public static LocalDate getLocalDate(String date){
        return LocalDate.parse(date,DateTimeFormatter.ofPattern("d/MM/yyyy"));
    }


}
