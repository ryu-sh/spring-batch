package io.springbatch.springbatch.itemProcessor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProcessorClassifier<C, T> implements Classifier<C, T> {

    Map<Integer, ItemProcessor<String, String>> map = new HashMap<>();

    @Override
    public T classify(C classifier) {
        String item = (String) classifier;
        String substring = item.substring(4);
        System.out.println("substring = " + substring);
        if (Integer.parseInt(substring) < 10) {
            return (T)new CustomItemProcessor();
        }
        else {
            return (T)new CustomItemProcessor2();
        }
    }

    public void setMap(Map<Integer, ItemProcessor<String, String>> map) {
        this.map = map;
    }
}
