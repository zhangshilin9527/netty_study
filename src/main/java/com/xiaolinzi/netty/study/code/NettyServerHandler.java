package com.xiaolinzi.netty.study.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author xiaolinzi
 * @Date 2021/3/31 15:49
 * @email xiaolinzi95_27@163.com
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有连接进来");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //使用StringDecoder解码
//        System.out.println("从客户端读取到String：" + msg.toString());
        //使用netty自带的ObjectDecoder解码
//        System.out.println("从客户端读取到Book：" + ((Book)msg).toString());
        //测试用protostuff对对象编解码
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        System.out.println("从客户端读取到 Book：" + ProtostuffUtil.deserializer(bytes, Book.class));
    }

}
