package com.jia.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyByteBuf01 {


    /**
     * netty的byteBuf 底层维护了 readerIndex writerIndex 和 capacity 将buffer分成了三个区域
     * readerIndex ---  已读取的区域
     * writerIndex ---  可读取的区域
     * capacity    ---  实际的大小
     * @param args
     */
    public static void main(String[] args) {

        ByteBuf byteBuf = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            byteBuf.writeByte(i);
        }


        System.out.println("capacity:" + byteBuf.capacity());

        // 可读取的区域变化了   这里越界了 抛出异常
        byteBuf.resetWriterIndex();
        for (int i = 0; i < byteBuf.capacity(); i++) {
            System.out.println(byteBuf.readByte());
        }
        System.out.println("------------");


    }

}
