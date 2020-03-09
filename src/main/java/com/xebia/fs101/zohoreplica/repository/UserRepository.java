package com.xebia.fs101.zohoreplica.repository;

import com.xebia.fs101.zohoreplica.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface UserRepository extends JpaRepository<User,UUID> {
    User findByUsername(String username);
}
