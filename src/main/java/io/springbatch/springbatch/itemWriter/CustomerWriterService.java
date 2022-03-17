package io.springbatch.springbatch.itemWriter;

public class CustomerWriterService<T> {
    public void customWrite(T item) {
        System.out.println("item = " + item);
    }
}
