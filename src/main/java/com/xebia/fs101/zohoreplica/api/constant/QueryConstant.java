package com.xebia.fs101.zohoreplica.api.constant;

public interface QueryConstant {
    String FIND_BY_KEYWORD_QUERY = "SELECT CAST(id as varchar) id,fullname FROM USERS where lower(fullname)" +
            " like %:keyword% or lower(username) like %:keyword% ";

    String ATTENDANCE_DETAILS_QUERY="SELECT * FROM ATTENDANCE a  WHERE a.date= ?1 AND " +
            "a.user_id= ?2 order by a.checkin";

}
