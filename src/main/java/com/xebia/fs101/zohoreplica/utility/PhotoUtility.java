package com.xebia.fs101.zohoreplica.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;

public class PhotoUtility {

    public static  byte[] compress(byte[] data) throws IOException {

        Deflater deflater=new Deflater();
        deflater.setInput(data);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream(data.length);
        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            byteArrayOutputStream.write(buffer, 0, count);
        }
        byteArrayOutputStream.close();
        byte[] output = byteArrayOutputStream.toByteArray();
        return output;
    }
}
