package io.springbatch.springbatch.faultTolerant.skip;

public class NoSkippableException extends RuntimeException {
    public NoSkippableException(String s) {
        super(s);
    }
}
