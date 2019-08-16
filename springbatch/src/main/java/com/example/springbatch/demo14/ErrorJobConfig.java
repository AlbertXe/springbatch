package com.example.springbatch.demo14;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * HASEE
 * 2019/7/27 2:19
 */
@Configuration
@EnableBatchProcessing
public class ErrorJobConfig {
    @Autowired
    JobBuilderFactory jobBuilderFactory;
    @Autowired
    StepBuilderFactory stepBuilderFactory;
    private int a =1;

    @Bean
    public Job errorJob() {
        return  jobBuilderFactory.get("errorJob")
                .start(errorStep1())
                .next(errorStep2())
                .build();
    }
    @Bean
    public Step errorStep2() {
        return stepBuilderFactory.get("errorStep2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        Map<String, Object> context = chunkContext.getStepContext().getStepExecutionContext();
                        a++;
                        if (a%2==1) {
                            System.out.println(chunkContext.getStepContext().getStepName()+"  first");
                            return RepeatStatus.FINISHED;
                        }else {
                            System.out.println(chunkContext.getStepContext().getStepName()+"  second");
                            throw new Exception("Error");
                        }
                    }
                }).build();
    }

    @Bean
    public Step errorStep1() {
        return stepBuilderFactory.get("errorStep1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        Map<String, Object> context = chunkContext.getStepContext().getStepExecutionContext();
                        a++;
                        if ( a%2 ==1) {
                            System.out.println(chunkContext.getStepContext().getStepName()+"  first");
                            return RepeatStatus.FINISHED;
                        }else {
                            System.out.println(chunkContext.getStepContext().getStepName()+"  second");
//                            chunkContext.getStepContext().getStepExecutionContext().put("fill", "fill");
//                            context.put("fill", "fill");
                            throw new RuntimeException("error");
                        }
                    }
                }).build();
    }
}
