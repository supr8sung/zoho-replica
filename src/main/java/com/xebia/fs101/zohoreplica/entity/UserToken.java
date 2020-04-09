package com.xebia.fs101.zohoreplica.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    private String username;
    @NotBlank
    private String token;
    @CreationTimestamp
    private Date createdAt;

    public UserToken() {

    }

    public UserToken(String username, String token ) {

        this.username = username;
        this.token = token;
    }

    public long getId() {

        return id;
    }

    public String getUsername() {

        return username;
    }

    public String getToken() {

        return token;
    }

}
