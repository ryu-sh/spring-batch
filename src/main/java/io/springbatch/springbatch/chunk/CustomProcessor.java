package io.springbatch.springbatch.chunk;

import io.springbatch.springbatch.domain.Customer;
import org.springframework.batch.item.ItemProcessor;

public class CustomProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer customer) throws Exception {
        customer.toUppercaseName();
        return customer;
    }
}
