package com.xebia.fs101.zohoreplica.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;
@Entity

@Table(name = "attendance",indexes = {@Index(name = "attendance_index", columnList = "date")})
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "date")
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

    public LocalTime checkinTime() {
        return checkin;
    }

    public LocalTime checkoutTime() {
        return checkout;
    }

    public User getUser() {
        return user;
    }

    public void setCheckout(LocalTime checkout) {
        this.checkout = checkout;
    }

    public void setDate(LocalDate date) {

        this.date = date;
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

        public Builder withUser(User val) {
            user = val;
            return this;
        }

        public Attendance build() {
            return new Attendance(this);
        }


    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", date=" + date +
                ", checkin=" + checkin +
                ", checkout=" + checkout +
                ", user=" + user +
                '}';
    }
}
