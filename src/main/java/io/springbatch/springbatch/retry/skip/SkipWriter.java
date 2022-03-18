package io.springbatch.springbatch.retry.skip;

import java.util.List;

public class SkipWriter implements org.springframework.batch.item.ItemWriter<String> {
    private int cnt = 0;

    @Override
    public void write(List<? extends String> items) throws Exception {
        for (String item : items) {
            if (item.equals("-12")) {
                System.out.println("itemWriter = " + item);
                throw new SkippableException("Process Failed!, cnt : " + cnt);
            }
            else {
                System.out.println("itemWriter = " + item);
            }
        }
    }
}
