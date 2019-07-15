package com.example.springbatch.demo11;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
@Component("flatFileWrite1")
public class FlatFileWrite implements ItemWriter<Customer> {
    @Override
    public void write(List<? extends Customer> items) throws Exception {
        for (Customer customer : items) {
            System.out.println(customer);
        }
    }
}
