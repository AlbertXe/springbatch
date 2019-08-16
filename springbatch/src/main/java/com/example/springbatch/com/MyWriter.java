package com.example.springbatch.com;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * HASEE
 * 2019/8/5 19:58
 */
public class MyWriter implements ItemWriter , ItemWriteListener {
    @Override
    public void write(List items) throws Exception {
        System.out.println(items);
    }

    @Override
    public void beforeWrite(List items) {
        System.out.println("before");
    }

    @Override
    public void afterWrite(List items) {
        System.out.println("after1");

    }

    @Override
    public void onWriteError(Exception exception, List items) {

    }
}
