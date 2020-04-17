package com.xebia.fs101.zohoreplica.api.response;

public class UserSearchResponse {
    private Long id;
    private String fullname;
    private byte[] photo;


    public UserSearchResponse(Long id, String fullname, byte[] photo) {
        this.id = id;
        this.fullname = fullname;
        this.photo = photo;

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
