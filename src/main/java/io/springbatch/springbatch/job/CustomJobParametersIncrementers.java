package io.springbatch.springbatch.job;

import org.springframework.batch.core.JobParameters;

public class CustomJobParametersIncrementers implements org.springframework.batch.core.JobParametersIncrementer {
    @Override
    public JobParameters getNext(JobParameters jobParameters) {
        return null;
    }
}
