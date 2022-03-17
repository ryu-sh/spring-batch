package io.springbatch.springbatch.itemProcessor;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor2 implements ItemProcessor<String, String> {
    int cnt = 0;
    @Override
    public String process(String item) throws Exception {
        return item + ++cnt;
    }
}
