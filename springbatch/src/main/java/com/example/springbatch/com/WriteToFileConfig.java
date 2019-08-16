package com.example.springbatch.com;

import com.example.springbatch.demo11.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * HASEE
 * 2019/8/5 17:14
 */
@Configuration
@EnableBatchProcessing
public class WriteToFileConfig{
    @Autowired
    JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job writeToFileJob92() {
        return jobBuilderFactory.get("writeToFileJob92")
                .start(writeToFileStep2())
                .build();
    }
@Bean
    public Step writeToFileStep2() {
        return stepBuilderFactory.get("writeToFileStep2")
                .chunk(100)
                .reader(writeToFileReader01())
                .processor(new ItemProcessor(){
                    @Override
                    public Object process(Object item) throws Exception {//只过来非空对象
                        return item;
                    }
                })
                .writer(writeToFileWriter01())
                .build();

    }
    @Bean
    @StepScope
    public ItemWriter<? super Object> writeToFileWriter01() {
        ListItemWriter2 writer = new ListItemWriter2();

        return new MyWriter();
    }
@Bean
@StepScope
    public ItemReader<?> writeToFileReader01() {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i <50 ; i++) {
            Customer customer = new Customer();
            customer.setId(Long.valueOf(i));
            customers.add(customer);
        }
        ListItemReader reader = new ListItemReader(customers);
        return reader;
    }
}
