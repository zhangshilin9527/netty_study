package com.xiaolinzi.netty.study.cheat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author xiaolinzi
 * @Date 2021/3/31 9:39
 * @email xiaolinzi95_27@163.com
 * @Description netty实现聊天室服务端
 */
public class ChatServer {
    public static void main(String[] args) throws Exception {
        //接受连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //处理业务
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //启动加载类
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //向pipeline加入解码器 继承ChannelInboundHandler
                            pipeline.addLast("decoder", new StringDecoder());
                            //向pipeline加入编码器  继承ChannelOutboundHandler
                            pipeline.addLast("encoder", new StringEncoder());
                            //加入自己的业务处理handler
                            pipeline.addLast(new ChatServerHandler());
                        }
                    });
            System.out.println("聊天室server启动。。");
            ChannelFuture channelFuture = bootstrap.bind(9000).sync();
            //关闭通道
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
