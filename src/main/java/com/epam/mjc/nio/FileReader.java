package com.epam.mjc.nio;

import java.io.File;


public class FileReader {

    public Profile getDataFromFile(File file) {
        String splitter = ":";
        String keyName = "Name";
        String keyAge = "Age";
        String keyEmail = "Email";
        String keyPhone = "Phone";
        Map<String, String> mapProfile = new HashMap<>();
        try(RandomAccessFile aFile = new RandomAccessFile(file.getAbsolutePath(), "r");
            FileChannel inChannel = aFile.getChannel()) {
            long fileSize = inChannel.size();
            ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
            inChannel.read(buffer);
            buffer.flip();
            StringBuilder stringBuilder = new StringBuilder();
            char charIn;
            char endLine = 0x0A;
            for (int i = 0; i < fileSize; i++) {
                String [] arrKeyVal;
                final int indKey = 0;
                final int indVal = 1;
                charIn = (char) buffer.get();
                if (charIn != endLine) stringBuilder.append(charIn);
                if (charIn == endLine) {
                    arrKeyVal = stringBuilder.toString().split(splitter);
                    mapProfile.put(arrKeyVal[indKey].trim(), arrKeyVal[indVal].trim());
                    stringBuilder.setLength(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Profile(mapProfile.get(keyName),
                Integer.parseInt(mapProfile.get(keyAge)),
                mapProfile.get(keyEmail),
                Long.parseLong(mapProfile.get(keyPhone)));
    }
}
