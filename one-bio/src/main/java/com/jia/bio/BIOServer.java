package com.jia.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {

    public static void main(String[] args) throws Exception {

//        思路：
//        1. 创建一个线程池
//        2. 如果有客户端连接， 就创建一个线程， 与之通讯

        ExecutorService executorService = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务端启动了");

//        一直在监听
        while(true) {

            final Socket accept = serverSocket.accept();
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        handler(accept);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }


    }


    //这个方法用来处理客户端发送过来的额消息
    public  static void handler(Socket socket) throws Exception{

        System.out.println("线程的信息 id:" + Thread.currentThread().getId() + "名称:" + Thread.currentThread().getName());
        byte[] bytes = new byte[1024];

        InputStream inputStream = socket.getInputStream();

//        循环读取客户端发送的数据
        while (true) {
        System.out.println("线程的信息 id:" + Thread.currentThread().getId() + "名称:" + Thread.currentThread().getName());
            int read = inputStream.read(bytes);
            if (read != -1) {
//                输出客户端发送的消息
                System.out.println(new String(bytes, 0, read));
            } else {
                break;
            }
        }

        System.out.println("关闭连接");
        socket.close();


    }

}
