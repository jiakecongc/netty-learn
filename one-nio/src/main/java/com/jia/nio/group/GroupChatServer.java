package com.jia.nio.group;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class GroupChatServer {

    private Selector selector;

    private ServerSocketChannel listenChannel;

    private static final int PORT= 6667;

//    进行初始化的工作
    public GroupChatServer() {

        try {

            //进行selector  serversocketChannel的初始化工作 绑定端口
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置为非阻塞模式
            listenChannel.configureBlocking(false);
            //输出channel到selector中
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //进行监听
    public void listen() {
        try {
            while (true) {
                int select = selector.select();
                //有事件进行处理
                if (select > 0) {
                    //循环遍历得到selectionKey集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        //取出selectionKey
                        SelectionKey key = iterator.next();

                        //监听到连接请求
                        if (key.isAcceptable()) {
                            SocketChannel accept = listenChannel.accept();
                            //设置为非阻塞
                            accept.configureBlocking(false);
                            //将这个socketChannel注册到selector中
                            accept.register(selector, SelectionKey.OP_READ);

                            //打印个提示
                            System.out.println(accept.getRemoteAddress() + "/上线");
                        }

                        //监听到读请求 即通道发送的是读事件
                        if (key.isReadable()) {
                            //处理读事件
                            readData(key);
                        }

                        // 两种事件都不是， 移除这个key
                        iterator.remove();
                    }
                } else {
                    System.out.println("等待连接....");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    // 读取客户端的信息
    private void readData(SelectionKey key) {
        SocketChannel channel = null;
        try {
            // 提取关联的channel
            channel = (SocketChannel) key.channel();

            // 创建buffer 并与channel 关联
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            // 读取到数据
            if (count > 0) {
                String msg = new String(buffer.array());
                System.out.println("from 客户端：" + msg);

                // 向其他客户端转发消息
                sendInfoToOtherClients(msg, channel);
            }
        } catch (Exception e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线了...");
                // 取消注册
                key.cancel();
                channel.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void sendInfoToOtherClients(String msg, SocketChannel channel) throws IOException {

        System.out.println("服务器转发消息中....");

        //遍历所有注册到seletor的socketChannel 并且排除自己
        for (SelectionKey key: selector.selectedKeys()) {
            // 通过key取出对应的channel
            Channel targetChannel = key.channel();
            //排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != channel) {
                SocketChannel targetChannel2 = (SocketChannel) targetChannel;

                //将消息存放在buffer中
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                // 将buffer数据写入通道中
                targetChannel2.write(buffer);
            }
        }
    }


    // 创建服务器对象
    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }


}
