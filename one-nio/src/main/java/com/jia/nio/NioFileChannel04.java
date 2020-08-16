package com.jia.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel04 {


    public static void main(String[] args) throws Exception {

        FileInputStream fileInputStream = new FileInputStream("D:\\file01.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\file02.txt");

        FileChannel inputStreamChannel = fileInputStream.getChannel();
        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        outputStreamChannel.transferFrom(inputStreamChannel, 0, inputStreamChannel.size());

        inputStreamChannel.close();
        outputStreamChannel.close();

        fileInputStream.close();
        fileOutputStream.close();

    }

}
