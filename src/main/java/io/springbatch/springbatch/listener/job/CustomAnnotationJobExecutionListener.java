package io.springbatch.springbatch.listener.job;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

public class CustomAnnotationJobExecutionListener {
    @BeforeJob
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("job is start!");
        System.out.println("job name : " + jobExecution.getJobInstance().getJobName());
    }

    @AfterJob
    public void afterJob(JobExecution jobExecution) {
        long startTime = jobExecution.getStartTime().getTime();
        long endTime = jobExecution.getEndTime().getTime();

        System.out.println("end - start : " + (endTime - startTime));
    }
}
