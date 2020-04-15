package com.xebia.fs101.zohoreplica.utility;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
class StringUtilityTest {

    @Test
    void should_generate_a_mail_header_with_your_name_initials() {
        String body = StringUtility.generateReportBody("Supreet Singh",
                                                       LocalTime.of(9,45,34), LocalTime.of(20,34,20));
        assertEquals(body.contains("Hi Supreet,"),true);
    }
}