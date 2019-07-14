package com.example.springbatch.demo5;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class DeciderDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    //创建决策器
    @Bean
    public JobExecutionDecider myDecider() {
        return  new MyDecider();
    }

    @Bean
    public Job deciderJob() {
        return jobBuilderFactory.get("deciderJob")
                .start(deciderStep0())
                .next(myDecider())
                .from(myDecider()).on("even").to(deciderStep1())
                .from(myDecider()).on("odd").to(deciderStep2())
                .from(deciderStep2()).on("*").to(myDecider())//执行2 之后返回到 决策器继续执行 好处是：?
                .end()
                .build();
    }
    @Bean
    public Step deciderStep0() {
        return stepBuilderFactory.get("deciderStep0").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("deciderStep0");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }
    @Bean
    public Step deciderStep1() {
        return stepBuilderFactory.get("deciderStep1").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("even");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }
    @Bean
    public Step deciderStep2() {
        return stepBuilderFactory.get("deciderStep2").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("odd");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }
}
