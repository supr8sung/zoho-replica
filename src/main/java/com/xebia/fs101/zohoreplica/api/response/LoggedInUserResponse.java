package com.xebia.fs101.zohoreplica.api.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

public class LoggedInUserResponse {
    private UUID userId;
    private String fullname;
    private String email;
    private String company;
    private String mobile;
    private long followersCount;
    private long followingCount;
    private LocalTime lastCheckin;
    private String totalHours;
    private LocalDate birthday;
    private Long checkinId;
    private Set<String> following;
    private Set<String> followers;
    private byte[] photo;

    public LoggedInUserResponse(String fullname, String email, String company, String mobile,
                                long followersCount, long followingCount, LocalTime lastCheckin,
                                LocalDate birthday, String totalHours, Long checkinId, UUID userId,
                                byte[] photo) {

        this.fullname = fullname;
        this.email = email;
        this.company = company;
        this.mobile = mobile;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
        this.lastCheckin = lastCheckin;
        this.birthday = birthday;
        this.totalHours = totalHours;
        this.checkinId = checkinId;
        this.userId = userId;
        this.photo = photo;
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
        totalHours = builder.totalHours;
        checkinId = builder.checkinId;
        userId = builder.userId;
        photo = builder.photo;
        followers = builder.followers;
        following = builder.following;
    }

    public Long getCheckinId() {

        return checkinId;
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

    public String getTotalHours() {

        return totalHours;
    }

    public byte[] getPhoto() {

        return photo;
    }

    public UUID getUserId() {

        return userId;
    }

    public Set<String> getFollowing() {

        return following;
    }

    public Set<String> getFollowers() {

        return followers;
    }

    public static final class Builder {
        private Set<String> following;
        private Set<String> followers;
        private String totalHours;
        private Long checkinId;
        private UUID userId;
        private byte[] photo;
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

        public Builder withBirthday(LocalDate val) {

            birthday = val;
            return this;
        }

        public Builder withTotalHours(String val) {

            totalHours = val;
            return this;
        }

        public Builder withCheckinId(Long val) {

            checkinId = val;
            return this;
        }

        public Builder withUserId(UUID val) {

            userId = val;
            return this;
        }

        public Builder withPhoto(byte[] val) {

            photo = val;
            return this;
        }

        public Builder withFollowing(Set<String> val) {

            following = val;
            return this;
        }

        public Builder withFollowers(Set<String> val) {

            followers = val;
            return this;
        }

        public LoggedInUserResponse build() {

            return new LoggedInUserResponse(this);
        }
    }
}
