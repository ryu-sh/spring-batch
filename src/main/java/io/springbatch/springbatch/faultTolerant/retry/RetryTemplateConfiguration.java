package io.springbatch.springbatch.faultTolerant.retry;

import io.springbatch.springbatch.faultTolerant.retry.domain.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RetryTemplateConfiguration {
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
                .<String, Customer>chunk(chunkSize)
                .reader(new ItemReader<String>() {
                    int i = 0;
                    @Override
                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        i++;
                        System.out.println("ItemReader :" + i);
                        return i > 10 ? null : String.valueOf(i);
                    }
                })
                .processor(retryItemProcessor())
                .writer(System.out::println)
                .faultTolerant()
//                .skip(RetryableException.class)
//                .skipLimit(2)
//                .retry(RetryableException.class)
//                .retryLimit(2)
                .build();
    }

    @Bean
    public ItemProcessor<? super String, Customer> retryItemProcessor() {
        return new RetryItemProcessor2();
    }

    @Bean
    public RetryTemplate retryTemplate() {
        Map<Class<? extends Throwable>, Boolean> exceptionClasses = new HashMap<>();
        exceptionClasses.put(RetryableException.class, true);

        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(2000);

        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(2, exceptionClasses);
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(simpleRetryPolicy);
//        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }
}
