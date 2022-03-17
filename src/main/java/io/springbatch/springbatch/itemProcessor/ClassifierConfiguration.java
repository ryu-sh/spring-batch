package io.springbatch.springbatch.itemProcessor;

import io.springbatch.springbatch.itemReader.CustomerService;
import io.springbatch.springbatch.itemWriter.CustomerWriterService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class ClassifierConfiguration {
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
                .processor(customItemProcessor())
                .writer(customItemWriter())
                .build();
    }

    @Bean
    public ItemProcessor customItemProcessor() {
        ClassifierCompositeItemProcessor processor = new ClassifierCompositeItemProcessor();
        ProcessorClassifier<String, ItemProcessor<String, String>> classifier = new ProcessorClassifier<>();
        Map<Integer, ItemProcessor<String, String>> processorMap = new HashMap<>();
        processorMap.put(1, new CustomItemProcessor());
        processorMap.put(2, new CustomItemProcessor2());
        classifier.setMap(processorMap);

        processor.setClassifier(classifier);

        return processor;
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
