package io.springbatch.springbatch.faultTolerant.retry;

import io.springbatch.springbatch.faultTolerant.retry.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.classify.BinaryExceptionClassifier;
import org.springframework.classify.Classifier;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.DefaultRetryState;
import org.springframework.retry.support.RetryTemplate;

public class RetryItemProcessor2 implements org.springframework.batch.item.ItemProcessor<String, Customer> {

    @Autowired
    private RetryTemplate retryTemplate;
    private int cnt = 0;

    @Override
    public Customer process(String item) throws Exception {
        System.out.println("ItemProcessor : " + item);

        Classifier<Throwable, Boolean> rollbackClassifier = new BinaryExceptionClassifier(true);

        Customer customer = retryTemplate.execute(
                new RetryCallback<Customer, RuntimeException>() {
                    @Override
                    public Customer doWithRetry(RetryContext retryContext) throws RuntimeException {
                        System.out.println("item = " + item);
                        if (item.equals("1") || item.equals("2")) {
                            cnt++;
                            throw new RetryableException("Retry Exception!! cnt : " + cnt);
                        }
                        return new Customer(item);
                    }
                },
                new RecoveryCallback<Customer>() {
                    @Override
                    public Customer recover(RetryContext retryContext) throws Exception {
                        return new Customer(item);
                    }
                },
                new DefaultRetryState(item, rollbackClassifier)
        );
        return customer;
    }
}