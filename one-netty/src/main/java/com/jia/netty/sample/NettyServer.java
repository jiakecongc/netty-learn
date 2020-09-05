package com.jia.netty.sample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {


    public static void main(String[] args) throws Exception{


        // 创建BossGroup 和 workerGroup
        // 创建两个线程组 bossGroup: 处理连接请求    workerGroup: 处理真正的业务请求
        // 两个都是无限循环   含有的子线程（NioEventLoop）个数  默认为： cpu核数的2倍

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建服务器端的启动对象
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            // 设置两个线程组
            // 设置服务器的通道实现
            // 设置线程队列的连接个数
            // 设置保持活动连接状态
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // 通过匿名对象 创建一个通道的测试对象
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });


            System.out.println("....服务器is ready ....");

            // 绑定一个端口并且同步，生成一个ChannelFuture对象
            //  启动服务器并且绑定端口
            ChannelFuture cf = serverBootstrap.bind(6668).sync();
            // 对关闭通道进行监听
            cf.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }


}
