package com.xebia.fs101.zohoreplica.utility;

import com.xebia.fs101.zohoreplica.model.UserDesignation;
import com.xebia.fs101.zohoreplica.security.ZohoApplicationRole;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.xebia.fs101.zohoreplica.model.UserDesignation.CONSULTANT;
import static com.xebia.fs101.zohoreplica.security.ZohoApplicationRole.EMPLOYEE;
import static com.xebia.fs101.zohoreplica.security.ZohoApplicationRole.MANAGER;
import static com.xebia.fs101.zohoreplica.utility.AttendanceUtility.calculateDailyHours;
public class StringUtility {

    public static String generateReportBody(String fullname, LocalTime inTime, LocalTime outTime){


        String hours= calculateDailyHours(inTime,outTime);
        StringBuilder body=new StringBuilder();
        body.append("Hi "+fullname.split(" ")[0]+",\n\n\n");  // taking name initials
        body.append(">>>>>>>> This is an auto generated mailer <<<<<<<<<<\n\n\n");

        body.append("In case you feel this is an error kindly raise ticket\n\n\n");
        body.append("Your In Time : "+ LocalDate.now()+" "+inTime+"\n");
        body.append("Your Out Time : "+ LocalDate.now()+" "+outTime+"\n");
        body.append("Total working Hours "+ hours+"\n\n\n");
        body.append(">>>>>>>> End Of Message <<<<<<<<<<\n\n\n");

        return body.toString();
    }


    public static String generateOtpBody(String otp,String fullname) {
        StringBuilder body=new StringBuilder();
        body.append("Hi "+fullname.split(" ")[0]+",\n\n\n");  // taking name initials


        body.append("Your OTP for changing password is "+ otp+ "\n");
        body.append("Use this password for validation. If you have not requested this please rasie " +
                "concern\n\n");

        body.append(">>>>>>>> End Of Message <<<<<<<<<<\n\n\n");
        return body.toString();
    }


    public static ZohoApplicationRole getRole(UserDesignation designation){
        if(designation== CONSULTANT)
            return EMPLOYEE;
        else
            return MANAGER;
    }
}
