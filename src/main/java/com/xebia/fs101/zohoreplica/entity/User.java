package com.xebia.fs101.zohoreplica.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xebia.fs101.zohoreplica.security.ZohoApplicationRole;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotBlank
    @Column(unique = true)
    private String username;
    @NotBlank
    private String fullname;
    @NotBlank
    @Email
    @Column(unique = true)
    private String email;
    @NotBlank
    private String mobile;
    @NotBlank
    private String password;
    @NotBlank
    private String company;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
    @Lob
    @JsonIgnore
    private byte[] photo;
    @Enumerated(value = EnumType.STRING)
    private ZohoApplicationRole role;

    private long followingCount;
    private long followersCount;
    @ElementCollection
    private List<User> followers;
    @ElementCollection
    private List<User> following;

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
        followersCount=builder.followersCount;
        followingCount=builder.followingCount;
        following=builder.following;
        followers=builder.followers;
    }

    public User(User user) {
        this.id = user.getId();
        this.username= user.getUsername();
        this.fullname= user.getFullname();
        this.password= user.getPassword();
        this.photo= user.getPhoto();
        this.role= user.getRole();
        this.email= user.getEmail();
        this.mobile= user.getMobile();
        this.company= user.getCompany();
        this.followersCount = user.getFollowersCount();
        this.followingCount = user.getFollowingCount();
        this.followers=user.getFollowers();
        this.following=user.getFollowing();
    }

    public UUID getId() {
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

    public String getCompany() {
        return company;
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

    public long getFollowingCount() {
        return followingCount;
    }

    public long getFollowersCount() {
        return followersCount;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFollowingCount(long followingCount) {
        this.followingCount = followingCount;
    }

    public void setFollowersCount(long followersCount) {
        this.followersCount = followersCount;
    }

    public ZohoApplicationRole getRole() {
        return role;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public static final class Builder {
        private UUID id;
        private @NotBlank String username;
        private @NotBlank String fullname;
        private @NotBlank @Email String email;
        private @NotBlank String mobile;
        private @NotBlank String password;
        private @NotBlank String company;
        private Date createdAt;
        private Date updatedAt;
        private byte[] photo;
        private ZohoApplicationRole role;
        private long followersCount;
        private long followingCount;
        private List<User> following;
        private List<User> followers;

        public Builder() {
        }

        public Builder withId(UUID val) {
            id = val;
            return this;
        }

        public Builder withUsername(@NotBlank String val) {
            username = val;
            return this;
        }

        public Builder withFullname(@NotBlank String val) {
            fullname = val;
            return this;
        }

        public Builder withEmail(@NotBlank @Email String val) {
            email = val;
            return this;
        }

        public Builder withMobile(@NotBlank String val) {
            mobile = val;
            return this;
        }

        public Builder withPassword(@NotBlank String val) {
            password = val;
            return this;
        }

        public Builder withCompany(@NotBlank String val) {
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

        public Builder withRole(ZohoApplicationRole val) {
            role = val;
            return this;
        }
        public Builder withFollowersCount(long val){
            followersCount =val;
            return this;
        }
        public Builder withFollowingCount(long val){
            followingCount =val;
            return this;
        }
        public Builder withFollowing(List<User> val){
            following=val;
            return this;
        }
        public Builder withFollowers(List<User> val){
            followers=val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", company='" + company + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
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
        return followingCount == user.followingCount &&
                followersCount == user.followersCount &&
                Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(fullname, user.fullname) &&
                Objects.equals(email, user.email) &&
                Objects.equals(mobile, user.mobile) &&
                Objects.equals(password, user.password) &&
                Objects.equals(company, user.company) &&
                Objects.equals(createdAt, user.createdAt) &&
                Objects.equals(updatedAt, user.updatedAt) &&
                Arrays.equals(photo, user.photo) &&
                role == user.role &&
                Objects.equals(followers, user.followers) &&
                Objects.equals(following, user.following);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, username, fullname, email, mobile, password, company, createdAt,
                updatedAt, role, followingCount, followersCount, followers, following);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }
}
