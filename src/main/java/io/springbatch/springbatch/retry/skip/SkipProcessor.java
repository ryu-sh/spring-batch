package io.springbatch.springbatch.retry.skip;

public class SkipProcessor implements org.springframework.batch.item.ItemProcessor<String, String> {
    private int cnt = 0;
    @Override
    public String process(String item) throws Exception {
        if (item.equals("6") || item.equals("7")) {
            System.out.println("itemProcessor = " + item);
            throw new SkippableException("Process Failed!, cnt : " + cnt);
        }
        else {
            System.out.println("itemProcessor = " + item);
            return String.valueOf(Integer.valueOf(item) * -1);
        }
    }
}
