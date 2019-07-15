package com.example.springbatch.demo10;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class DbWrite implements ItemWriter<User> {
    @Override
    public void write(List<? extends User> items) throws Exception {
        for (User user : items) {
            System.out.println(user);
        }
    }
}
