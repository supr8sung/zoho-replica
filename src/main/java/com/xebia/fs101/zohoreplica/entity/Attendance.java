package com.xebia.fs101.zohoreplica.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;
@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalDate date;
    private LocalTime checkin;
    private LocalTime checkout;
    @ManyToOne
    private Employee employee;

    public Attendance(){

    }


    public long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getCheckinHours() {
        return checkin;
    }

    public LocalTime getCheckoutHours() {
        return checkout;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Attendance(long id, LocalDate date, LocalTime checkin, LocalTime checkout, Employee employee) {
        this.id = id;
        this.date = date;
        this.checkin = checkin;
        this.checkout = checkout;
        this.employee = employee;
    }

    private Attendance(Builder builder) {
        id = builder.id;
        date = builder.date;
        checkin = builder.checkin;
        checkout = builder.checkout;
        employee = builder.employee;
    }


    public static final class Builder {
        private long id;
        private LocalDate date;
        private LocalTime checkin;
        private LocalTime checkout;
        private Employee employee;

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

        public Builder withEmployee(Employee val) {
            employee = val;
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
                ", user=" + employee +
                '}';
    }
}
