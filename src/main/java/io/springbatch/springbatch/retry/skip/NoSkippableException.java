package io.springbatch.springbatch.retry.skip;

public class NoSkippableException extends RuntimeException {
    public NoSkippableException(String s) {
        super(s);
    }
}
