package io.springbatch.springbatch.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
@RequiredArgsConstructor
public class TaskletStepConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job taskletJob() {
        return jobBuilderFactory.get("taskletJob")
                .start(taskletStep1())
                .build();
    }

    @Bean
    public Step taskletStep1() {
        return stepBuilderFactory.get("helloStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("===================");
                    System.out.println("hello spring batch");
                    System.out.println("===================");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step chunkStep() {
        return stepBuilderFactory.get("chunkStep")
                .<String, String>chunk(10)
                .reader(new ListItemReader<>(Arrays.asList("item1", "item2", "item3")))
                .processor((ItemProcessor<String, String>) s -> s.toUpperCase(Locale.ROOT))
                .writer(list -> list.forEach(item -> System.out.println("item = " + item)))
                .build();
    }
}
