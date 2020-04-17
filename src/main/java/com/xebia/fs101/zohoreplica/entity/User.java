package com.xebia.fs101.zohoreplica.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xebia.fs101.zohoreplica.api.response.LoggedInUserResponse;
import com.xebia.fs101.zohoreplica.api.response.UserViewResponse;
import com.xebia.fs101.zohoreplica.model.Birthday;
import com.xebia.fs101.zohoreplica.model.Clients;
import com.xebia.fs101.zohoreplica.model.UserCity;
import com.xebia.fs101.zohoreplica.security.ZohoApplicationRole;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import static com.xebia.fs101.zohoreplica.utility.DateUtility.calculateAge;

@Entity
@Table(name = "users", indexes = {@Index(name = "user_index", columnList = "username")})
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String username;
    private String fullname;
    @Column(unique = true)
    private String email;
    private String mobile;
    private int designation;
    @Enumerated(value = EnumType.STRING)
    private UserCity city;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Clients company;
    private LocalDate birthday;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
    @OneToOne
    @JsonBackReference
    private User reportingTo;
    @Lob
    @JsonIgnore
    private byte[] photo;
    @Enumerated(value = EnumType.STRING)
    private ZohoApplicationRole role;
    private long followingCount;
    private long followersCount;
    @ElementCollection
    private Set<String> followers;
    @ElementCollection
    private Set<String> following;

    public User() {

    }

    private User(Builder builder) {

        id = builder.id;
        username = builder.username;
        fullname = builder.fullname;
        email = builder.email;
        mobile = builder.mobile;
        password = builder.password;
        company = builder.company;
        createdAt = builder.createdAt;
        updatedAt = builder.updatedAt;
        setPhoto(builder.photo);
        role = builder.role;
        followersCount = builder.followersCount;
        followingCount = builder.followingCount;
        following = builder.following;
        followers = builder.followers;
        birthday = builder.birthday;
        city = builder.city;
        designation = builder.designation;
        reportingTo = builder.reportingTo;
    }

    public User(User user) {

        this.id = user.getId();
        this.username = user.getUsername();
        this.fullname = user.getFullname();
        this.password = user.getPassword();
        this.photo = user.getPhoto();
        this.role = user.getRole();
        this.email = user.getEmail();
        this.mobile = user.getMobile();
        this.company = user.getCompany();
        this.followersCount = user.getFollowersCount();
        this.followingCount = user.getFollowingCount();
        this.followers = user.getFollowers();
        this.following = user.getFollowing();
        this.designation = user.getDesignation();
        this.city = user.getCity();
    }

    public Long getId() {

        return id;
    }

    public String getUsername() {

        return username;
    }

    public String getFullname() {

        return fullname;
    }

    public String getEmail() {

        return email;
    }

    public String getMobile() {

        return mobile;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public Clients getCompany() {

        return company;
    }

    public LocalDate getBirthday() {

        return birthday;
    }

    public Date getCreatedAt() {

        return createdAt;
    }

    public Date getUpdatedAt() {

        return updatedAt;
    }

    public byte[] getPhoto() {

        return photo;
    }

    public void setPhoto(byte[] photo) {

        this.photo = photo;
    }

    public User getReportingTo() {

        return reportingTo;
    }

    public int getDesignation() {

        return designation;
    }

    public UserCity getCity() {

        return city;
    }

    public long getFollowingCount() {

        return followingCount;
    }

    public void setFollowingCount(long followingCount) {

        this.followingCount = followingCount;
    }

    public long getFollowersCount() {

        return followersCount;
    }

    public void setFollowersCount(long followersCount) {

        this.followersCount = followersCount;
    }

    public Set<String> getFollowers() {

        return followers;
    }

    public void setFollowers(Set<String> followers) {

        this.followers = followers;
    }

    public Set<String> getFollowing() {

        return following;
    }

    public void setFollowing(Set<String> following) {

        this.following = following;
    }

    public ZohoApplicationRole getRole() {

        return role;
    }

    public void setReportingTo(User reportingTo) {

        this.reportingTo = reportingTo;
    }


    @Override
    public String toString() {

        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", designation=" + designation +
                ", city=" + city +
                ", password='" + password + '\'' +
                ", company='" + company + '\'' +
                ", birthday=" + birthday +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", reportingTo=" + reportingTo +
                ", photo=" + Arrays.toString(photo) +
                ", role=" + role +
                ", followingCount=" + followingCount +
                ", followersCount=" + followersCount +
                ", followers=" + followers +
                ", following=" + following +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getFollowingCount() == user.getFollowingCount() &&
                getFollowersCount() == user.getFollowersCount() &&
                Objects.equals(getId(), user.getId()) &&
                Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getFullname(), user.getFullname()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getMobile(), user.getMobile()) &&
                getDesignation() == user.getDesignation() &&
                getCity() == user.getCity() &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getCompany(), user.getCompany()) &&
                Objects.equals(getBirthday(), user.getBirthday()) &&
                Objects.equals(getCreatedAt(), user.getCreatedAt()) &&
                Objects.equals(getUpdatedAt(), user.getUpdatedAt()) &&
                Objects.equals(reportingTo, user.reportingTo) &&
                Arrays.equals(getPhoto(), user.getPhoto()) &&
                getRole() == user.getRole() &&
                Objects.equals(getFollowers(), user.getFollowers()) &&
                Objects.equals(getFollowing(), user.getFollowing());
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(getId(), getUsername(), getFullname(), getEmail(), getMobile(),
                                  getDesignation(), getCity(), getPassword(), getCompany(),
                                  getBirthday(), getCreatedAt(), getUpdatedAt(), reportingTo,
                                  getRole(),
                                  getFollowingCount(), getFollowersCount(), getFollowers(),
                                  getFollowing());
        result = 31 * result + Arrays.hashCode(getPhoto());
        return result;
    }

    public Birthday getBirthdayDetails() {

        return new Birthday.Builder()
                .withId(this.id)
                .withFullname(this.fullname)
                .withPhoto(this.photo)
                .withAge(calculateAge(this.birthday))
                .build();
    }

    public UserViewResponse toUserViewResponse() {

        return new UserViewResponse.Builder()
                .withFullname(this.fullname)
                .withCompany(this.company)
                .withEmail(this.email)
                .withMobile(this.mobile)
                .withFollowersCount(this.followersCount)
                .withFollowingCount(this.followingCount)
                .withFollowers(this.followers)
                .withFollowing(this.following)
                .withPhoto(this.photo)
                .build();
    }

    public LoggedInUserResponse toLoggedInUserResponse(LocalTime lastcheckin, String totalHours,
                                                       Long checkinId) {

        return new LoggedInUserResponse.Builder()
                .withUserId(this.id)
                .withFullname(this.fullname)
                .withCompany(this.company)
                .withEmail(this.email)
                .withMobile(this.mobile)
                .withFollowersCount(this.followersCount)
                .withFollowingCount(this.followingCount)
                .withLastCheckin(lastcheckin)
                .withBirthday(this.birthday)
                .withTotalHours(totalHours)
                .withCheckinId(checkinId)
                .withPhoto(this.photo)
                .withFollowers(this.followers)
                .withFollowing(this.following)
                .withUsername(this.username)
                .withReportingTo(this.reportingTo)
                .build();
    }

    public static final class Builder {
        private Long id;
        private String username;
        private String fullname;
        private String email;
        private String mobile;
        private String password;
        private Clients company;
        private Date createdAt;
        private LocalDate birthday;
        private Date updatedAt;
        private byte[] photo;
        private ZohoApplicationRole role;
        private long followersCount;
        private long followingCount;
        private Set<String> following;
        private Set<String> followers;
        private int designation;
        private UserCity city;
        private User reportingTo;

        public Builder() {

        }

        public Builder withId(Long val) {

            id = val;
            return this;
        }

        public Builder withUsername(String val) {

            username = val;
            return this;
        }

        public Builder withFullname(String val) {

            fullname = val;
            return this;
        }

        public Builder withEmail(String val) {

            email = val;
            return this;
        }

        public Builder withMobile(String val) {

            mobile = val;
            return this;
        }

        public Builder withPassword(String val) {

            password = val;
            return this;
        }

        public Builder withCompany(Clients val) {

            company = val;
            return this;
        }

        public Builder withCreatedAt(Date val) {

            createdAt = val;
            return this;
        }

        public Builder withUpdatedAt(Date val) {

            updatedAt = val;
            return this;
        }

        public Builder withPhoto(byte[] val) {

            photo = val;
            return this;
        }

        public Builder withBirthDay(LocalDate val) {

            birthday = val;
            return this;
        }

        public Builder withRole(ZohoApplicationRole val) {

            role = val;
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

        public Builder withFollowing(Set<String> val) {

            following = val;
            return this;
        }

        public Builder withFollowers(Set<String> val) {

            followers = val;
            return this;
        }

        public Builder withCity(UserCity val) {

            city = val;
            return this;
        }

        public Builder withDesignation(int val) {

            designation = val;
            return this;
        }

        public Builder withReportingTo(User val) {

            reportingTo = val;
            return this;
        }

        public User build() {

            return new User(this);
        }
    }
}
