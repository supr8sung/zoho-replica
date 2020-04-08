package com.xebia.fs101.zohoreplica.repository;

import com.xebia.fs101.zohoreplica.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import static com.xebia.fs101.zohoreplica.api.constant.QueryConstant.DELETE_EXPIRED_TOKENS;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken,Long> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = DELETE_EXPIRED_TOKENS)
    void deleteExpiredToken();
}
