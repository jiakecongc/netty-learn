package com.jia.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

//Scattering:将数据写入到buffer中， 可以采用buffer数组 依次写入
//Gathering: 从buffer 读取数据， 可以采用buffer数组， 依次读取
public class ScatteringAndGatheringTest {

    public static void main(String[] args) throws Exception{


        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

//        将端口绑定到socket中， 并启动
        serverSocketChannel.socket().bind(inetSocketAddress);
//        创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

//        等待客户端的连接
        SocketChannel socketChannel = serverSocketChannel.accept();
//          从客户端读取8个字节
        int messageLength = 8;

        while (true) {
            int byteRead = 0;

            while (byteRead < messageLength) {
                long read = socketChannel.read(byteBuffers);
                byteRead += read;
                System.out.println("byteRead=" + byteRead);

                Arrays.asList(byteBuffers)
                        .stream()
                        .map(buffer -> "postion=" + buffer.position() +", limit=" + buffer.limit())
                        .forEach(System.out::println);
            }
//            将所有的byteBuffer翻转
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());

            long byteWrite = 0;
            while (byteWrite < messageLength) {
                long write = socketChannel.write(byteBuffers);
                byteWrite += write;
            }

            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());

            System.out.println("byteRead=" + byteRead + ", byteWrite=" + byteWrite + ", messageLength=" + messageLength);

        }
    }

}
