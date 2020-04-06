package com.xebia.fs101.zohoreplica.api.response;

import java.util.UUID;
public class UserSearchResponse {
    private UUID id;
    private String fullname;
    private byte[] photo;


    public UserSearchResponse(UUID id, String fullname, byte[] photo) {
        this.id = id;
        this.fullname = fullname;
        this.photo = photo;

    }



    public UUID getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public byte[] getPhoto() {
        return photo;
    }




}
