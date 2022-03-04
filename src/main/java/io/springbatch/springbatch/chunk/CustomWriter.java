package io.springbatch.springbatch.chunk;

import io.springbatch.springbatch.domain.Customer;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class CustomWriter implements ItemWriter<Customer> {

    @Override
    public void write(List<? extends Customer> customers) throws Exception {
        for (Customer customer : customers) {
            System.out.println("customer = " + customer.getName());
        }
        System.out.println("end");
    }
}
