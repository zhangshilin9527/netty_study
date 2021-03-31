package com.xiaolinzi.netty.study.code;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author xiaolinzi
 * @Date 2021/3/31 15:48
 * @email xiaolinzi95_27@163.com
 */
public class NettyServer {
    public static void main(String[] args) throws Exception {
        //接受连接线程池
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //处理业务线程池，默认是cpu凉杯
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //加入解码器，接受新的信息时触发
//                            pipeline.addLast(new StringDecoder());
                            //加入编码器，当发出信息时候进行编码后再发出
//                            pipeline.addLast(new StringEncoder());
                            //加入netty自带object编码器 maxObjectSize是channel最大容量
//                            pipeline.addLast(new ObjectDecoder(10240, ClassResolvers.cacheDisabled(null)));
                            //加入服务端的业务处理器
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });

            System.out.println("netty server start。。");
            //
            ChannelFuture channelFuture = serverBootstrap.bind(9000).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
