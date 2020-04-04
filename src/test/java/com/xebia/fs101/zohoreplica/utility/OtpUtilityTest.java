package com.xebia.fs101.zohoreplica.utility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
class OtpUtilityTest {
    @Test
    void should_generate_a_random_otp() {
        String otp = OtpUtility.generateOtp();

        assertTrue(Integer.valueOf(otp)<10000,"genrated OTP should be of 4 digits");
    }
}