package io.springbatch.springbatch.faultTolerant.retry;

public class RetryItemProcessor implements org.springframework.batch.item.ItemProcessor<String, String> {
    private int cnt = 0;

    @Override
    public String process(String item) throws Exception {
        System.out.println("ItemProcessor : " + item);
        if (item.equals("2") || item.equals("3")) {
            cnt++;
            throw new RetryableException("Retry Exception!! cnt : " + cnt);
        }
        return item;
    }
}
