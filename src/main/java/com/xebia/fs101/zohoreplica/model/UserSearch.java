package com.xebia.fs101.zohoreplica.model;

import java.util.UUID;

public class UserSearch {

    private Long id;
    private String fullname;
    private byte[] photo;


    public UserSearch() {

    }

    public UserSearch(Long id, String fullname,byte[] photo) {

        this.photo=photo;
        this.id = id;
        this.fullname = fullname;

    }

    public Long getId() {

        return id;
    }

    public String getFullname() {

        return fullname;
    }

    public byte[] getPhoto() {

        return photo;
    }
}
