package com.jia.netty.sample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;


/**
 * 自定义的handler 需要继承netty规定好的某个handlerAdatper
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * ChannelHandlerContext ctx: 上下文对象， 含有管道pipeline 通道channel 地址
     * Object msg： 就是客户端发送的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("服务器读取线程：" + Thread.currentThread().getName());
        System.out.println("server ctx: " + ctx);
        System.out.println("看看channel和pipeline的关系");

        Channel channel = ctx.channel();
        // 其实pipeline就是一个双向链接
        ChannelPipeline pipeline = ctx.pipeline();

        // ByteBuf是有netty提供的， 不是NIO的ByteBuffer
        ByteBuf byteBuf  = (ByteBuf) msg;

        System.out.println("客户端发送的消息是:" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端的地址是：" + channel.remoteAddress());
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // writeAndFlush 就是write 和 flush
        // 将数据写入缓存中 并且刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端甲壳虫！！", CharsetUtil.UTF_8));

    }


    //  进行异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
