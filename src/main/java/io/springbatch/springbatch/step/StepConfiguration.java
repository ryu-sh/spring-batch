package io.springbatch.springbatch.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StepConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job stepJob() {
        return jobBuilderFactory.get("stepJob")
                .start(stepStep1())
                .next(stepStep2())
                .next(stepStep3())
                .build();
    }

    @Bean
    public Step stepStep1() {
        return stepBuilderFactory.get("helloStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("===================");
                    System.out.println("hello spring batch");
                    System.out.println("===================");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step stepStep2() {
        return stepBuilderFactory.get("helloStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("===================");
                    System.out.println("step2 was executed");
                    throw new RuntimeException("step2 exception");
                }).build();
    }

    @Bean
    public Step stepStep3() {
        return stepBuilderFactory.get("helloStep3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("===================");
                    System.out.println("step3 was executed");
                    System.out.println("===================");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
