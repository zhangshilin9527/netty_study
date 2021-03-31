package com.xiaolinzi.netty.study.heartbeat;

import com.xiaolinzi.netty.study.code.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author xiaolinzi
 * @Date 2021/3/31 18:29
 * @email xiaolinzi95_27@163.com
 */
public class HeartBeatClient {
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
                            pipeline.addLast(new StringDecoder());
                            //加入编码器，当发出信息时候进行编码后再发出
                            pipeline.addLast(new StringEncoder());
                            //加入客服端的业务处理器
                            pipeline.addLast(new HeartBeatClientHandler());
                        }
                    });

            System.out.println("netty client start。。");
            //同步连接服务端
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9000).sync();
            Channel channel = channelFuture.channel();
            while (channel.isActive()) {
                //每两秒发一次心跳检测
                Thread.sleep(2000);
//                每四秒进行一次心跳检测
//                Thread.sleep(4000);
                channel.writeAndFlush("心跳检测");
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}
