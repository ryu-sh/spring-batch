package io.springbatch.springbatch.retry.skip;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.LimitCheckingItemSkipPolicy;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class SkipConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private int chunkSize = 5;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("batchjob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<String, String>chunk(chunkSize)
                .reader(new ItemReader<String>() {
                    int i = 0;
                    @Override
                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        i++;
                        if (i == 3) {
                            throw new SkippableException("skip!");
                        }
                        System.out.println("ItemReader :" + i);
                        return i > 20 ? null : String.valueOf(i);
                    }
                })
                .processor(new SkipProcessor())
                .writer(new SkipWriter())
                .faultTolerant()
                .skipPolicy(limitCheckingItemSkipPolicy())
//                .skip(SkippableException.class)
//                .noSkip(NoSkippableException.class)
//                .skipLimit(4)
                .build();
    }

    @Bean
    public SkipPolicy limitCheckingItemSkipPolicy() {
        Map<Class<? extends Throwable>, Boolean> exceptionClasses = new HashMap<>();
        exceptionClasses.put(SkippableException.class, true);

        LimitCheckingItemSkipPolicy limitCheckingItemSkipPolicy = new LimitCheckingItemSkipPolicy(4, exceptionClasses);

        return limitCheckingItemSkipPolicy;
    }
}
