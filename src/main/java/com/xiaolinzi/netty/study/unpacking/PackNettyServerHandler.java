package com.xiaolinzi.netty.study.unpacking;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author xiaolinzi
 * @Date 2021/3/30 17:22
 * @email xiaolinzi95_27@163.com
 */
public class PackNettyServerHandler extends SimpleChannelInboundHandler<MyMessageProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessageProtocol msg) throws Exception {
        System.out.println("====服务端接收到消息如下====");
        System.out.println("长度=" + msg.getLen());
        System.out.println("内容=" + new String(msg.getContent(), CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
