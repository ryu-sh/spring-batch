package io.springbatch.springbatch.faultTolerant.skip;

public class SkippableException extends RuntimeException {
    public SkippableException(String s) {
        super(s);
    }
}
