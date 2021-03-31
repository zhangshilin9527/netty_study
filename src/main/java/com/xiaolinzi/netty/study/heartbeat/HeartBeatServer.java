package com.xiaolinzi.netty.study.heartbeat;

import com.xiaolinzi.netty.study.code.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaolinzi
 * @Date 2021/3/31 18:23
 * @email xiaolinzi95_27@163.com
 */
public class HeartBeatServer {
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
                            pipeline.addLast(new StringDecoder());
                            //加入编码器，当发出信息时候进行编码后再发出
                            pipeline.addLast(new StringEncoder());
                            //加入心跳检测处理器
                            // readerIdleTime  读超时时间
                            // writerIdleTime  写超时时间
                            // allIdleTime     总的超时时间
                            pipeline.addLast(new IdleStateHandler(3, 0, 0, TimeUnit.SECONDS));
                            //加入服务端的业务处理器
                            pipeline.addLast(new HeartBeatServerHandler());
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
