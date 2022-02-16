package io.springbatch.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ExecutionContextConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ExecutionContextTasklet1 executionContextTasklet1;
    private final ExecutionContextTasklet2 executionContextTasklet2;
    private final ExecutionContextTasklet3 executionContextTasklet3;

    @Bean
    public Job executionJob() {
        return jobBuilderFactory.get("executionJob")
                .start(executionStep1())
                .next(executionStep2())
                .next(executionStep3())
                .build();
    }

    @Bean
    public Step executionStep1() {
        return stepBuilderFactory.get("helloStep1")
                .tasklet(executionContextTasklet1).build();
    }

    @Bean
    public Step executionStep2() {
        return stepBuilderFactory.get("helloStep2")
                .tasklet(executionContextTasklet2).build();
    }

    @Bean
    public Step executionStep3() {
        return stepBuilderFactory.get("helloStep3")
                .tasklet(executionContextTasklet3).build();
    }
}
