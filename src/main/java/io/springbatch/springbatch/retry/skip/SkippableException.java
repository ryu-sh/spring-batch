package io.springbatch.springbatch.retry.skip;

public class SkippableException extends RuntimeException {
    public SkippableException(String s) {
        super(s);
    }
}
