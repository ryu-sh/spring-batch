package io.springbatch.springbatch.listener.job;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class CustomStepExecutionListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("step1 is start!");
        System.out.println("step1 name : " + stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        ExitStatus exitStatus = stepExecution.getExitStatus();
        BatchStatus batchStatus = stepExecution.getStatus();
        System.out.println("exitStatus = " + exitStatus);
        System.out.println("batchStatus = " + batchStatus);

        return ExitStatus.COMPLETED;
    }
}
