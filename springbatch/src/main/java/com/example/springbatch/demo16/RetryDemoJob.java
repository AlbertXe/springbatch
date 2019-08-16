package com.example.springbatch.demo16;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
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
 * 2019/7/27 9:41
 */
@Configuration
@EnableBatchProcessing
public class RetryDemoJob {
    @Autowired
    JobBuilderFactory jobBuilderFactory;
    @Autowired
    StepBuilderFactory stepBuilderFactory;
    @Autowired
    RetryProcessor retryProcessor;

    @Bean
    public Job retryDemoJob3() {
        return  jobBuilderFactory.get("retryDemoJob3")
                .start(retryStep3())
                .build();
    }
@Bean
    public Step retryStep3() {
        return stepBuilderFactory.get("retryStep3")
                .<String,String>chunk(10)
                .reader(retryReader())
                .processor(retryProcessor)
                .writer(retryWrite())
                .faultTolerant()
//                .retry(RuntimeException.class)
//                .retryLimit(5)
                .skip(RuntimeException.class)
                .skipLimit(5)
                .build();
    }
    @Bean
    @StepScope
    public ItemReader<String> retryReader() {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < 60; i++) {
            list.add(i + "");

        }
        return new ListItemReader<String>(list);
    }
@Bean
@StepScope
    public ItemWriter<String> retryWrite() {
        return (item)->{
            System.out.println(item);
        };
    }
}
