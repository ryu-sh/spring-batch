package io.springbatch.springbatch.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SimpleJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob")
                .start(simpleStep())
                .next(simpleStep2())
//                .validator(new CustomJobParametersValidator())
//                .validator(new DefaultJobParametersValidator(new String[]{"name", "date"}, new String[]{"count"}))
//                .preventRestart()
//                .incrementer(new CustomJobParametersIncrementers())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step simpleStep() {
        return stepBuilderFactory.get("simpleStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("===================");
                    System.out.println("hello spring batch");
                    System.out.println("===================");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step simpleStep2() {
        return stepBuilderFactory.get("simpleStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("===================");
                    System.out.println("step2 was executed");
                    System.out.println("===================");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
