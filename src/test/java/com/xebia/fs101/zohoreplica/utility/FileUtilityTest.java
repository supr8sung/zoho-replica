package com.xebia.fs101.zohoreplica.utility;

import com.xebia.fs101.zohoreplica.model.Birthday;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;

class FileUtilityTest {
    @Test
    void should_save_birthday_details_to_file() {

        Birthday birthday = new Birthday.Builder()
                .withId(new Long(1234))
                .withAge(24)
                .withPhoto(new byte[]{})
                .withFullname("Supreet Singh")
                .build();
        try {
            FileUtility.saveBirthdayDetails(Collections.singletonList(birthday));
            Path path= Paths.get("birthday.tmp");
            assertThat((Iterable<? extends Path>) path).isNotNull();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void get_birthday_details_from_a_file() throws IOException {

        Birthday birthday = new Birthday.Builder()
                .withId(new Long(145464))
                .withAge(24)
                .withPhoto(new byte[]{})
                .withFullname("Supreet Singh")
                .build();
        FileUtility.saveBirthdayDetails(Collections.singletonList(birthday));
        try {
            List<Birthday> birthdays = FileUtility.getAllBirthDays();
            assertThat(birthdays.get(0).getFullname()).isEqualTo("Supreet Singh");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}