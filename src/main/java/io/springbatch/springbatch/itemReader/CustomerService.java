package io.springbatch.springbatch.itemReader;

import org.springframework.stereotype.Service;

public class CustomerService<T> {
    private int cnt = 0;

    public T customRead() {
        if (cnt > 15) {
            return null;
        }
        return (T)("item" + cnt++);
    }
}
