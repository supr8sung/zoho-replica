package com.xebia.fs101.zohoreplica.utility;

import java.util.Random;
public class OtpUtility {
    public static String generateOtp(){
        return String.format("%04d", new Random().nextInt(10000));
    }

}
