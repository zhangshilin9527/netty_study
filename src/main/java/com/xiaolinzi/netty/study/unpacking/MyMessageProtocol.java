package com.xiaolinzi.netty.study.unpacking;


import lombok.Data;

/**
 * 自定义协议包
 */
@Data
public class MyMessageProtocol {

    //定义一次发送包体长度
    private int len;
    //一次发送包体内容
    private byte[] content;
}
