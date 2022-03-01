package io.springbatch.springbatch.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.step.job.DefaultJobParametersExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
@RequiredArgsConstructor
public class JobStepConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job parentJob() {
        return jobBuilderFactory.get("parentJob")
                .start(jobStep1(null))
                .next(parentStep2())
                .build();
    }

    @Bean
    public Step jobStep1(JobLauncher jobLauncher) {
        return stepBuilderFactory.get("jobStep1")
                .job(childJob())
                .launcher(jobLauncher)
                .parametersExtractor(jobParameterExtractor())
                .build();
    }

    private DefaultJobParametersExtractor jobParameterExtractor() {
        DefaultJobParametersExtractor extractor = new DefaultJobParametersExtractor();
        extractor.setKeys(new String[]{"name"});
        return extractor;
    }

    @Bean
    public Job childJob() {
        return jobBuilderFactory.get("childJob")
                .start(childStep())
                .build();
    }

    @Bean
    public Step childStep() {
        return stepBuilderFactory.get("childStep")
                .tasklet((stepContribution, chunkContext) -> null)
                .build();
    }

    @Bean
    public Step parentStep2() {
        return stepBuilderFactory.get("parentStep1")
                .tasklet((stepContribution, chunkContext) -> null)
                .build();
    }
}
