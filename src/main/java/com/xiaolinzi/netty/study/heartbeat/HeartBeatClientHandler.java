package com.xiaolinzi.netty.study.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author xiaolinzi
 * @Date 2021/3/31 18:31
 * @email xiaolinzi95_27@163.com
 */
public class HeartBeatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(" client received :" + msg);
        if (msg != null && msg.equals("服务端关闭连接")) {
            System.out.println(" 服务端关闭连接，客户端也关闭");
            ctx.channel().closeFuture();
        }
    }
}
