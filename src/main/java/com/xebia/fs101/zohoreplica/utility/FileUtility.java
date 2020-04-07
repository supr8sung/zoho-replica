package com.xebia.fs101.zohoreplica.utility;

import com.xebia.fs101.zohoreplica.model.Birthday;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class FileUtility {
    public static void saveBirthdayDetails(List<Birthday> birthdays) throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream("birthday.tmp");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(birthdays);
        objectOutputStream.close();
    }

    public static List<Birthday> getAllBirthDays() throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream("birthday.tmp");
        ObjectInputStream ois = new ObjectInputStream(fis);
        List<Birthday> birthdays = (List<Birthday>) ois.readObject();
        ois.close();
        return birthdays;
    }
}







