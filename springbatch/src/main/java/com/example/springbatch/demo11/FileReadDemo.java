package com.example.springbatch.demo11;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.BindException;

import java.util.Random;

@Configuration
@EnableBatchProcessing
public class FileReadDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ItemWriter<? super Customer> flatFileWrite1;

    @Bean
    public Job fileReadDemoJob2() {
        return jobBuilderFactory.get("fileReadDemoJob2")
                .start(fileReadStep1())
                .build();
    }

    @Bean
    public Step fileReadStep1() {
        return stepBuilderFactory.get("fileReadStep1")
                .<Customer, Customer>chunk(100)
                .reader(flatFileReader())
                .writer(flatFileWrite1)
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Customer> flatFileReader() {
        FlatFileItemReader reader = new FlatFileItemReader();
        reader.setResource(new ClassPathResource("customer.txt"));
        reader.setLinesToSkip(1);//跳过第一行

        //数据解析
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id", "firstName", "lastName", "birthday");
        //映射对象
        DefaultLineMapper<Customer> mapper = new DefaultLineMapper<>();
        mapper.setLineTokenizer(tokenizer);
        mapper.setFieldSetMapper(new FieldSetMapper<Customer>() {
            @Override
            public Customer mapFieldSet(FieldSet fieldSet) throws BindException {
                Customer customer = new Customer();
                customer.setId(fieldSet.readLong("id"));
                customer.setFirstName(fieldSet.readString("firstName"));
                customer.setLastName(fieldSet.readString("lastName"));
                customer.setBirthDay(fieldSet.readString("birthday"));
                return customer;
            }
        });
        mapper.afterPropertiesSet();
        reader.setLineMapper(mapper);
        return reader;
    }

    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 1; i <= 100; i++) {
            String firstName = "";
            String lastName = "";
            for (int j = 1; j <= 5; j++) {
                firstName += String.valueOf((char) (random.nextInt(25) + 65));
                lastName += String.valueOf((char) (random.nextInt(25) + 97));
            }
            System.out.println(i + "," + firstName + "," + lastName + ",2019-07-10 14:11:23");
        }
    }
}
