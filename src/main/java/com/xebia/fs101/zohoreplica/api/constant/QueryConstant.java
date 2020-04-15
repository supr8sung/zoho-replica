package com.xebia.fs101.zohoreplica.api.constant;

public interface QueryConstant {
    String FIND_BY_KEYWORD_QUERY = "SELECT CAST(id as varchar) id,fullname,photo FROM USERS where lower(fullname)" +
            " like %:keyword% or lower(username) like %:keyword% ";

    String ATTENDANCE_DETAILS_QUERY="SELECT * FROM ATTENDANCE a  WHERE a.date= ?1 AND " +
            "a.user_id= ?2 order by a.checkin";
    String  FIND_BIRTHDAY_QUERY="select * from users where extract(month from birthday)= ?1 " +
            "AND extract(day from birthday)= ?2";

    String DELETE_EXPIRED_TOKENS_QUERY ="delete from user_token where created_at + interval '1 minutes' <= now()";

    //String  ALL_REPORTING_QUERY="select CAST(id as varchar) id,fullname from users where city= ?1 and designation > ?2";
    String  ALL_REPORTING_QUERY="Select * FROM users  where CITY= ?1 and designation > ?2";

}
