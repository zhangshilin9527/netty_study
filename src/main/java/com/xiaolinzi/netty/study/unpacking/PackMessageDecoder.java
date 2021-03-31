package com.xiaolinzi.netty.study.unpacking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PackMessageDecoder extends ByteToMessageDecoder {

    int length = 0;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //int类型长度4
        if(in.readableBytes() >= 4) {
            //获取到长度
            if (length == 0){
                length = in.readInt();
            }
            if (in.readableBytes() < length) {
                System.out.println("当前可读数据不够，继续等待。。");
                return;
            }
            byte[] content = new byte[length];
            if (in.readableBytes() >= length){
                in.readBytes(content);

                //封装成MyMessageProtocol对象，传递到下一个handler业务处理
                MyMessageProtocol messageProtocol = new MyMessageProtocol();
                messageProtocol.setLen(length);
                messageProtocol.setContent(content);
                //交给下个handler处理
                out.add(messageProtocol);
            }
            length = 0;
        }
    }
}
