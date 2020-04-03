package com.xebia.fs101.zohoreplica.api.response;

import java.time.LocalDate;
import java.time.LocalTime;
public class LoggedInUserResponse {
    private String fullname;
    private String email;
    private String company;
    private String mobile;
    private long followersCount;
    private long followingCount;
    private LocalTime lastCheckin;
    private LocalDate birthday;


    public LoggedInUserResponse(String fullname, String email, String company, String mobile,
                                long followersCount, long followingCount, LocalTime lastCheckin,
                                LocalDate birthday) {
        this.fullname = fullname;
        this.email = email;
        this.company = company;
        this.mobile = mobile;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
        this.lastCheckin = lastCheckin;
        this.birthday = birthday;
    }

    private LoggedInUserResponse(Builder builder) {
        fullname = builder.fullname;
        email = builder.email;
        company = builder.company;
        mobile = builder.mobile;
        followersCount = builder.followersCount;
        followingCount = builder.followingCount;
        lastCheckin = builder.lastCheckin;
        birthday = builder.birthday;
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

    public LocalTime getLastCheckin() {
        return lastCheckin;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public static final class Builder {
        private LocalDate birthday;
        private String fullname;
        private String email;
        private String company;
        private String mobile;
        private long followersCount;
        private long followingCount;
        private LocalTime lastCheckin;

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

        public Builder withLastCheckin(LocalTime val) {
            lastCheckin = val;
            return this;
        }
        public Builder withBirthday(LocalDate val){
            birthday=val;
            return this;
        }

        public LoggedInUserResponse build() {
            return new LoggedInUserResponse(this);
        }
    }
}
