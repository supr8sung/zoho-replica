package com.xebia.fs101.zohoreplica.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;
@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalDate date;
    private LocalTime checkin;
    private LocalTime checkout;
    @ManyToOne
    private User user;

    public Attendance(){

    }


    public long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getCheckin() {
        return checkin;
    }

    public LocalTime getCheckout() {
        return checkout;
    }

    public User getUser() {
        return user;
    }

    public Attendance(long id, LocalDate date, LocalTime checkin, LocalTime checkout, User user) {
        this.id = id;
        this.date = date;
        this.checkin = checkin;
        this.checkout = checkout;
        this.user = user;
    }

    private Attendance(Builder builder) {
        id = builder.id;
        date = builder.date;
        checkin = builder.checkin;
        checkout = builder.checkout;
        user = builder.user;
    }


    public static final class Builder {
        private long id;
        private LocalDate date;
        private LocalTime checkin;
        private LocalTime checkout;
        private User user;

        public Builder() {
        }

        public Builder withId(long val) {
            id = val;
            return this;
        }

        public Builder withDate(@NotBlank LocalDate val) {
            date = val;
            return this;
        }

        public Builder withCheckin(LocalTime val) {
            checkin = val;
            return this;
        }

        public Builder withCheckout(LocalTime val) {
            checkout = val;
            return this;
        }

        public Builder withUsername(User val) {
            user = val;
            return this;
        }

        public Attendance build() {
            return new Attendance(this);
        }
    }
}
