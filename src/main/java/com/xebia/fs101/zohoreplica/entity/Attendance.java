package com.xebia.fs101.zohoreplica.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    private LocalDate date;
    private LocalTime checkin;
    private LocalTime checkout;
    @ManyToOne
    private UUID userId;

    private Attendance(Builder builder) {
        id = builder.id;
        date = builder.date;
        checkin = builder.checkin;
        checkout = builder.checkout;
        userId = builder.userId;
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

    public UUID getUserId() {
        return userId;
    }

    public Attendance(long id, @NotBlank LocalDate date, LocalTime checkin, LocalTime checkout, UUID userId) {
        this.id = id;
        this.date = date;
        this.checkin = checkin;
        this.checkout = checkout;
        this.userId = userId;
    }

    public static final class Builder {
        private long id;
        private @NotBlank LocalDate date;
        private LocalTime checkin;
        private LocalTime checkout;
        private UUID userId;

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

        public Builder withUserId(UUID val) {
            userId = val;
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
                ", userId=" + userId +
                '}';
    }
}
