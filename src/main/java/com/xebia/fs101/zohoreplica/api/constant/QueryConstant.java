package com.xebia.fs101.zohoreplica.api.constant;

public interface QueryConstant {
       String CHECKOUT="UPDATE  Attendance  set checkout=:time where user_id=:userId and " +
            "date=:date";
}
