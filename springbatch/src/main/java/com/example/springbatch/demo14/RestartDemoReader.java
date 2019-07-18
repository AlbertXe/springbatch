package com.example.springbatch.demo14;

import com.example.springbatch.demo12.Customer;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.BindException;

public class RestartDemoReader implements ItemStreamReader<Customer> {
    private FlatFileItemReader<Customer> flatFileItemReader = new FlatFileItemReader();
    private Long currLine = 0L;
    private Boolean restart = false;
    private ExecutionContext executionContext;

    public RestartDemoReader() {
        flatFileItemReader.setResource(new ClassPathResource("customer.txt"));
        flatFileItemReader.setLinesToSkip(1);
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id","firstName","lastName","birthday");
        DefaultLineMapper<Customer> mapper = new DefaultLineMapper<>();
        mapper.setFieldSetMapper(new FieldSetMapper<Customer>() {
            @Override
            public Customer mapFieldSet(FieldSet fieldSet) throws BindException {
                Customer customer = new Customer();
                customer.setId(fieldSet.readLong(1));
                customer.setFirstName(fieldSet.readString(2));
                customer.setLastName(fieldSet.readString(3));
                customer.setBirthDay(fieldSet.readString(4));
                return customer;
            }
        });
    }

    @Override
    public Customer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        //处理读的异常
        Customer customer = null;
        currLine ++;
        if (restart) {
            flatFileItemReader.setLinesToSkip(currLine.intValue()-1);
            restart = false;
            System.out.println("start read form line:"+currLine);
        }
        flatFileItemReader.open(executionContext);
        customer = flatFileItemReader.read();

        if (customer != null && customer.getFirstName().equals("wrongName")) {
            throw new RuntimeException("Something wrong,:"+customer.toString());
        }
        return customer;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.executionContext = executionContext;
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void close() throws ItemStreamException {

    }
}
