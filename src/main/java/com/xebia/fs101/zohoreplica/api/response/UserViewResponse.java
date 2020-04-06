package com.xebia.fs101.zohoreplica.api.response;

import java.util.Set;
public class UserViewResponse {

    private String fullname;
    private String email;
    private String company;
    private String mobile;
    private long followersCount;
    private long followingCount;
    private Set<String> followers;
    private Set<String> following;
    private byte[] photo;


    public UserViewResponse(String fullname, String email, String company, String mobile,
                            long followersCount, long followingCount, Set<String> followers,
                            Set<String> following, byte[] photo) {
        this.fullname = fullname;
        this.email = email;
        this.company = company;
        this.mobile = mobile;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
        this.followers = followers;
        this.following = following;
        this.photo = photo;
    }

    private UserViewResponse(Builder builder) {
        fullname = builder.fullname;
        email = builder.email;
        company = builder.company;
        mobile = builder.mobile;
        followersCount = builder.followersCount;
        followingCount = builder.followingCount;
        followers=builder.followers;
        following=builder.following;
        photo=builder.photo;

    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getCompany() {
        return company;
    }

    public String getMobile() {
        return mobile;
    }

    public long getFollowersCount() {
        return followersCount;
    }

    public long getFollowingCount() {
        return followingCount;
    }

    public Set<String> getFollowers() {
        return followers;
    }

    public Set<String> getFollowing() {
        return following;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public static final class Builder {
        private Set<String> followers;
        private Set<String> following;
        private String fullname;
        private String email;
        private String company;
        private String mobile;
        private long followersCount;
        private long followingCount;
        private  byte[] photo;


        public Builder() {
        }

        public Builder withFullname(String val) {
            fullname = val;
            return this;
        }

        public Builder withEmail(String val) {
            email = val;
            return this;
        }

        public Builder withCompany(String val) {
            company = val;
            return this;
        }

        public Builder withMobile(String val) {
            mobile = val;
            return this;
        }

        public Builder withFollowersCount(long val) {
            followersCount = val;
            return this;
        }

        public Builder withFollowingCount(long val) {
            followingCount = val;
            return this;
        }
        public  Builder withFollowing(Set<String> val){
            following=val;
            return this;
        }
        public Builder withFollowers(Set<String > val){
            followers =val;
            return this;
        }
        public Builder withPhoto(byte[] val){
            photo=val;
            return  this;

        }

        public UserViewResponse build() {
            return new UserViewResponse(this);
        }
    }
}
