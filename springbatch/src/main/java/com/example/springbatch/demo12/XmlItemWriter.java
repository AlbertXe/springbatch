package com.example.springbatch.demo12;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class XmlItemWriter implements ItemWriter<Customer> {
    @Override
    public void write(List<? extends Customer> items) throws Exception {
        for (Customer customer : items) {
            System.out.println(customer);
        }
    }
}
