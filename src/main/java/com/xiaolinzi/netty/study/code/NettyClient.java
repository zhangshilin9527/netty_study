package com.xiaolinzi.netty.study.code;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyClient {
    public static void main(String[] args) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //加入解码器，接受新的信息时触发
//                            pipeline.addLast(new StringDecoder());
                            //加入编码器，当发出信息时候进行编码后再发出
//                            pipeline.addLast(new StringEncoder());
                            //加入netty自带object编码器 实体类不需要实现Serializable
//                            pipeline.addLast(new ObjectEncoder());
                            //加入客服端的业务处理器
                            pipeline.addLast(new NettyClientHandler());
                        }
                    });

            System.out.println("netty client start。。");
            //同步连接服务端
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9000).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
