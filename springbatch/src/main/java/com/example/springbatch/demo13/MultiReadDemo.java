package com.example.springbatch.demo13;

import com.example.springbatch.demo11.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.validation.BindException;

@Configuration
public class MultiReadDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Value("classpath:file*.txt")
    private Resource[] resources;


    @Bean
    public Job multiFileReadDemo() {
        return jobBuilderFactory.get("multiFileReadDemo")
                .start(multiReadStep())
                .build();

    }

    @Bean
    public Step multiReadStep() {
        return stepBuilderFactory.get("multiReadStep")
                .<Customer, Customer>chunk(100)
                .reader(multiReader())
                .writer(xmlItemWriter())
                .build();
    }
    @Bean
    @StepScope
    public ItemWriter xmlItemWriter() {
        return new FlatFileItemWriter();
    }

    @Bean
    @StepScope
    public ItemReader<? extends Customer> multiReader() {
        MultiResourceItemReader reader = new MultiResourceItemReader();
        reader.setDelegate(multiFlatFileReader());
        reader.setResources(resources);
        return reader;
    }

    @Bean
    @StepScope
    public ResourceAwareItemReaderItemStream multiFlatFileReader() {
        FlatFileItemReader reader = new FlatFileItemReader();
        reader.setResource(new ClassPathResource("customer.txt"));
        reader.setLinesToSkip(1);//跳过第一行

        //数据解析
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id", "firstName", "lastName", "birthday");
        //映射对象
        DefaultLineMapper<com.example.springbatch.demo11.Customer> mapper = new DefaultLineMapper<>();
        mapper.setLineTokenizer(tokenizer);
        mapper.setFieldSetMapper(new FieldSetMapper<com.example.springbatch.demo11.Customer>() {
            @Override
            public com.example.springbatch.demo11.Customer mapFieldSet(FieldSet fieldSet) throws BindException {
                com.example.springbatch.demo11.Customer customer = new com.example.springbatch.demo11.Customer();
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
}
