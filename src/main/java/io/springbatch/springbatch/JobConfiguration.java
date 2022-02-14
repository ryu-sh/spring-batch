package io.springbatch.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((stepContribution, chunkContext) -> {
                    JobParameters jobParameters = stepContribution.getStepExecution().getJobParameters();
                    jobParameters.getParameters().forEach((s, jobParameter) -> {
                        System.out.println("paramter = " + s);
                        System.out.println("jobParameter = " + jobParameter);
                    });
                    System.out.println("============================");
                    Map<String, Object> jobParameters1 = chunkContext.getStepContext().getJobParameters();
                    for (String s : jobParameters1.keySet()) {
                        System.out.println("key = " + s);
                        System.out.println(jobParameters1.get(s));
                    }
                    System.out.println("step1 was executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("step1 was executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
