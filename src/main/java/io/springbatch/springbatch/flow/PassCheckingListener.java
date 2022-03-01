package io.springbatch.springbatch.flow;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class PassCheckingListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (!stepExecution.getExitStatus().equals(ExitStatus.FAILED)) {
            return new ExitStatus("PASS");
        }
        return null;
    }
}
