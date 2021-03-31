package com.xiaolinzi.netty.study.unpacking;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author xiaolinzi
 * @Date 2021/3/30 18:15
 * @email xiaolinzi95_27@163.com
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<MyMessageProtocol> {
    /**
     * 当客户端连接服务器完成就会触发该方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i = 0; i< 10; i++) {
            String msg = "检测拆包";
            //创建协议包对象
            MyMessageProtocol messageProtocol = new MyMessageProtocol();
            messageProtocol.setLen(msg.getBytes(CharsetUtil.UTF_8).length);
            messageProtocol.setContent(msg.getBytes(CharsetUtil.UTF_8));
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessageProtocol msg) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
