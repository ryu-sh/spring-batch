package io.springbatch.springbatch.itemWriter;

import io.springbatch.springbatch.itemReader.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class JpaItemWriterConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager em;
    private int chunkSize = 2;

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
                .<Customer, Customer2>chunk(chunkSize)
                .reader(customerItemReader())
                .processor(customProcessor())
                .writer(customItemWriter())
                .build();
    }

    private ItemProcessor<? super Customer, ? extends Customer2> customProcessor() {
        return Customer::toCustomer2;
    }

    @Bean
    public ItemReader<Customer> customerItemReader() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("firstname", "A%");

        return new JpaPagingItemReaderBuilder<Customer>()
                .name("jpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
//                .queryString("select c from Customer c")
                .queryString("select c from Customer c")
                .build();
    }

    @Bean
    public ItemWriter<Customer2> customItemWriter() {
        return new JpaItemWriterBuilder<Customer2>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
