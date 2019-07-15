package com.example.springbatch.demo10;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * JDBC 读数据库
 */
@Configuration
@EnableBatchProcessing
public class DbDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    DataSource dataSource;
    @Autowired
    private ItemWriter<User> dbWrite;

    @Bean
    public Job dbDemo5() {
        return jobBuilderFactory.get("dbDemo5")
                .start(dbStep1())
                .build();
    }
@Bean
    public Step dbStep1() {
        return stepBuilderFactory.get("dbStep1")
                .<User,User>chunk(2)
                .reader(dbReader())
                .writer(dbWrite)
                .build();

    }
@Bean
@StepScope
    public JdbcPagingItemReader<User> dbReader() {
        JdbcPagingItemReader<User> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setFetchSize(2);
        //将读取的记录 转成User
    reader.setRowMapper(new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt(1));
            user.setUsername(rs.getString(2));
            user.setPassword(rs.getString(3));
            user.setAge(rs.getInt(4));
            return user;
        }
    });
    //指定sql
    MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();
    provider.setSelectClause("id,username,password,age");//指定查询字段
    provider.setFromClause("from user");//表

    //指定字段排序
    Map<String , Order> sort = new HashMap<>();
    sort.put("age", Order.DESCENDING);
    sort.put("id", Order.DESCENDING);
    provider.setSortKeys(sort);

    reader.setQueryProvider(provider);

        return reader;
    }
}
