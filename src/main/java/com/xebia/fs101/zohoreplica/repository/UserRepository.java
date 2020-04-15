package com.xebia.fs101.zohoreplica.repository;

import com.xebia.fs101.zohoreplica.api.response.UserSearchResponse;
import com.xebia.fs101.zohoreplica.entity.User;
import com.xebia.fs101.zohoreplica.model.UserCity;
import com.xebia.fs101.zohoreplica.model.UserSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.xebia.fs101.zohoreplica.api.constant.QueryConstant.ALL_REPORTING_QUERY;
import static com.xebia.fs101.zohoreplica.api.constant.QueryConstant.FIND_BIRTHDAY_QUERY;
import static com.xebia.fs101.zohoreplica.api.constant.QueryConstant.FIND_BY_KEYWORD_QUERY;

@Transactional
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);


    @Query(value = FIND_BY_KEYWORD_QUERY,nativeQuery = true)
    public List<Object[]> search(@Param("keyword") String keyword);

    @Query(value = FIND_BIRTHDAY_QUERY,nativeQuery = true)
    public List<User> allBirthdaysOn(int month, int day);

    @Query(value = ALL_REPORTING_QUERY,nativeQuery = true)
    List<User> findAllReportings(String  city, int designation);
}
