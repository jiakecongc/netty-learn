package com.jia.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class NettyByteBuf02 {


    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!", Charset.forName("utf-8"));


        if (byteBuf.hasArray()) {
            byte[] array = byteBuf.array();


            System.out.println(new String(array, Charset.forName("utf-8")));
            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());

            System.out.println(byteBuf.getByte(0));

            int len = byteBuf.readableBytes();

            for (int i = 0; i < len; i++){
                System.out.println((char) byteBuf.getByte(i));
            }

        }

    }

}
