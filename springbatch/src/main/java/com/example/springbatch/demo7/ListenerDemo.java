package com.example.springbatch.demo7;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class ListenerDemo {
    @Autowired
    JobBuilderFactory jobBuilderFactory;
    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job listenerJob() {
        return jobBuilderFactory.get("listenerJob")
                .start(listenStep1())
                .listener(new MyJobListener())
                .build();
    }
    @Bean
    public Step listenStep1() {
        return stepBuilderFactory.get("listenStep1")
                .<String,String>chunk(2)  // 读2个数据就处理
                .faultTolerant()
                .listener(new MyChunkListener())
                .reader(reader())
                .writer(write())
                .build();
    }
    @Bean
    public ItemWriter<String> write() {
        return new ItemWriter<String>() {
            @Override
            public void write(List<? extends String> items) throws Exception {
                for (String item : items) {
                    System.out.println(item);
                }
            }
        };
    }
    @Bean
    public ItemReader<String> reader() {
        return new ListItemReader<>(Arrays.asList("java","spring","mybatis"));
    }

}
