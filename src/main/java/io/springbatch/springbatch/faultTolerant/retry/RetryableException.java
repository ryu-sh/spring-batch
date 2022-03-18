package io.springbatch.springbatch.faultTolerant.retry;

public class RetryableException extends RuntimeException {
    public RetryableException(String message) {
        super(message);
    }
}
