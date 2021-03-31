package com.xiaolinzi.netty.study.code;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("收到服务器消息:" + msg);
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //使用StringEncoder编码
//        ctx.writeAndFlush("测试String编解码");
        //测试netty自带对象编解码
//        ctx.writeAndFlush(new Book("西行纪",13));
        //测试用protostuff编码
        ByteBuf buf = Unpooled.copiedBuffer(ProtostuffUtil.serializer(new Book("西行纪2", 11)));
        ctx.writeAndFlush(buf);

    }
}
