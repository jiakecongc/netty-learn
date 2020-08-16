package com.jia.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel01 {


    public static void main(String[] args) throws Exception {

        String str = "hello 大家";

//        创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\file01.txt");

//        通过fileOutputStream创建FileChannel
        FileChannel fileChannel = fileOutputStream.getChannel();
//        创建一个ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());

//        需要对byteBuffer进行反转
        byteBuffer.flip();

//        byteBuffer内容放入fileChannel
        fileChannel.write(byteBuffer);

        fileOutputStream.close();


    }

}
