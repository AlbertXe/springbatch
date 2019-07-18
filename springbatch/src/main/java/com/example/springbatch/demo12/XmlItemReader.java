package com.example.springbatch.demo12;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class XmlItemReader {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ItemWriter<Customer> xmlFileWriter;

    @Bean
    public Job xmlItemReaderJob() {
        return jobBuilderFactory.get("xmlItemReaderJob")
                .start(xmlReaderStep())
                .build();
    }
    @Bean
    public Step xmlReaderStep() {
        return stepBuilderFactory.get("xmlReaderStep")
                .<Customer,Customer>chunk(100)
                .reader(xmlFileReader())
                .writer(xmlFileWriter)
                .build();
    }
    @Bean
    @StepScope
    public StaxEventItemReader<Customer> xmlFileReader() {
        StaxEventItemReader<Customer> reader = new StaxEventItemReader<>();
        reader.setResource(new ClassPathResource("customer.xml"));

        reader.setFragmentRootElementName("customer");
        Map <String,Class> map = new HashMap<>();
        map.put("customer", Customer.class);
//        XStreamMarshaller marshaller = new XStreamMarshaller();
        return reader;
    }
}
