package com.xebia.fs101.zohoreplica.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;
public class Birthday implements Serializable {
    private UUID id;
    private String fullname;
    private byte[] photo;
    private int age;

    public Birthday(UUID id, String fullname, byte[] photo, int age) {
        this.id = id;
        this.fullname = fullname;
        this.photo = photo;
        this.age = age;
    }

    public UUID getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public int getAge() {
        return age;
    }

    private Birthday(Builder builder) {
        id = builder.id;
        fullname = builder.fullname;
        photo = builder.photo;
        age = builder.age;
    }

    public static final class Builder {
        private UUID id;
        private String fullname;
        private byte[] photo;
        private int age;

        public Builder() {
        }

        public Builder withId(UUID val) {
            id = val;
            return this;
        }

        public Builder withFullname(String val) {
            fullname = val;
            return this;
        }

        public Builder withPhoto(byte[] val) {
            photo = val;
            return this;
        }

        public Builder withAge(int val) {
            age = val;
            return this;
        }

        public Birthday build() {
            return new Birthday(this);
        }
    }

    @Override
    public String toString() {

        return "Birthday{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", age=" + age +
                '}';
    }
}
