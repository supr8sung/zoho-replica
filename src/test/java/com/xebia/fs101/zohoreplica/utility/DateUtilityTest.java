package com.xebia.fs101.zohoreplica.utility;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
class DateUtilityTest {


    @Test
    void should_give_date_out_of_string() {
        LocalDate date = DateUtility.getLocalDate("02/11/1209");
        assertEquals(LocalDate.of(1209,11,02),date);

    }

    @Test
    void should_calculate_age_for_a_user() {
        int age = DateUtility.calculateAge(LocalDate.of(1996, 07, 18));
        assertEquals(23,age);



    }


}