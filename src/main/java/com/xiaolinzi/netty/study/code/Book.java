package com.xiaolinzi.netty.study.code;


import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaolinzi
 * @Date 2021/3/31 16:08
 * @email xiaolinzi95_27@163.com
 */
@Data
public class Book implements Serializable {
    private String name;
    private int page;


    public Book(String name, int page) {
        this.name = name;
        this.page = page;
    }

    public Book() {
    }


}
