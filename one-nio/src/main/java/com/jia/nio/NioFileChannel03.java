package com.jia.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel03 {


    public static void main(String[] args) throws Exception {

        FileInputStream fileInputStream = new FileInputStream("D:\\file01.txt");
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("D:\\file02.txt");
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true) {
            byteBuffer.clear();
            int read = fileInputStreamChannel.read(byteBuffer);
            if (read == -1) {
                break;
            }

            byteBuffer.flip();

            fileOutputStreamChannel.write(byteBuffer);

        }

        fileInputStreamChannel.close();
        fileOutputStreamChannel.close();

        fileInputStream.close();
        fileOutputStream.close();

    }

}
