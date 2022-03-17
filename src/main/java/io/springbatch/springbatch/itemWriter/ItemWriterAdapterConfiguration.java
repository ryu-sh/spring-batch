package io.springbatch.springbatch.itemWriter;

import io.springbatch.springbatch.itemReader.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ItemWriterAdapterConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private int chunkSize = 10;

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
                .reader(customItemReader())
                .writer(customItemWriter())
                .build();
    }

    @Bean
    public ItemReaderAdapter customItemReader() {
        ItemReaderAdapter reader = new ItemReaderAdapter<>();
        reader.setTargetObject(new CustomerService<String>());
        reader.setTargetMethod("customRead");

        return reader;
    }

    @Bean
    public ItemWriterAdapter customItemWriter() {
        ItemWriterAdapter writer = new ItemWriterAdapter();
        writer.setTargetObject(new CustomerWriterService<String>());
        writer.setTargetMethod("customWrite");

        return writer;
    }
}
