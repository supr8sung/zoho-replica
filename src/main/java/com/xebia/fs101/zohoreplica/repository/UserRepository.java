package com.xebia.fs101.zohoreplica.repository;

import com.xebia.fs101.zohoreplica.api.response.UserSearchResponse;
import com.xebia.fs101.zohoreplica.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static com.xebia.fs101.zohoreplica.api.constant.QueryConstant.FIND_BIRTHDAY_QUERY;
import static com.xebia.fs101.zohoreplica.api.constant.QueryConstant.FIND_BY_KEYWORD_QUERY;
@Transactional
public interface UserRepository extends JpaRepository<User,UUID> {
    User findByUsername(String username);


    @Query(value = FIND_BY_KEYWORD_QUERY,nativeQuery = true)
    public List<Object[]> search(@Param("keyword") String keyword);

    @Query(value = FIND_BIRTHDAY_QUERY,nativeQuery = true)
    public List<User> allBirthday(int month, int day);


}
