package com.xebia.fs101.zohoreplica.repository;

import com.xebia.fs101.zohoreplica.api.constant.QueryConstant;
import com.xebia.fs101.zohoreplica.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

import static com.xebia.fs101.zohoreplica.api.constant.QueryConstant.FIND_BY_NAME;
public interface UserRepository extends JpaRepository<User,UUID> {
    User findByUsername(String username);


    @Query(value = FIND_BY_NAME,nativeQuery = true)
    public List<String> search(@Param("keyword") String keyword);


}
