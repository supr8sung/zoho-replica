//package com.xebia.fs101.zohoreplica.utility;
//
//import com.xebia.fs101.zohoreplica.model.Birthday;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class FileUtilityTest {
//    @Test
//    void should_save_birthday_details_to_file() {
//
//        Birthday birthday = new Birthday.Builder()
//                .withId(UUID.randomUUID())
//                .withAge(24)
//                .withPhoto(new byte[]{})
//                .withFullname("Supret Sigh")
//                .build();
//        try {
//            FileUtility.saveBirthdayDetails(Arrays.asList(birthday));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    void get_birthday_details_from_a_file() {
//
//        try {
//            List<Birthday> birthdays = FileUtility.readBirthDay();
//            birthdays.forEach(System.out::println);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//}