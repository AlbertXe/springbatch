package com.example.springbatch.demo9;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class ItemReaderDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job readJob() {
        return jobBuilderFactory.get("readJob")
                .start(readStep1())
                .build();
    }
    @Bean
    public Step readStep1() {
        return stepBuilderFactory.get("readStep1")
                .<String,String>chunk(2)
                .reader(itemReader())
                .writer(list->{
                    for (String s : list) {
                        System.out.println(s+"......");
                    }
                })
                .build();
    }
    @Bean
    public ItemReader<String> itemReader() {
        List<String> data = Arrays.asList("cat", "dog", "pig", "chuck");
        return new MyReader(data);
    }
}
