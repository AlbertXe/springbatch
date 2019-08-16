package com.example.springbatch.com;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.support.ListItemWriter;

import java.util.List;

/**
 * HASEE
 * 2019/8/5 19:49
 */
public class ListItemWriter2 extends ListItemWriter implements ItemWriteListener {
    @Override
    public void beforeWrite(List items) {
        System.out.println("before size============="+items.size());
    }

    @Override
    public void afterWrite(List items) {
        System.out.println("after size============="+items.size());
    }

    @Override
    public void onWriteError(Exception exception, List items) {

    }
}
