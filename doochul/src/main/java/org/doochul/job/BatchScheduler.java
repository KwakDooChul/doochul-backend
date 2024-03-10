package org.doochul.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job sandNotificationAfterLessonJob;
    private final Job sandNotificationBeforeLessonJob;
    private final Job updateRemainingCountJob;

    public BatchScheduler(JobLauncher jobLauncher,
                          @Qualifier("sandNotificationAfterLessonJob") Job sandNotificationAfterLessonJob,
                          @Qualifier("sandNotificationBeforeLessonJob") Job sandNotificationBeforeLessonJob,
                          @Qualifier("updateRemainingCountJob") Job updateRemainingCountJob) {
        this.jobLauncher = jobLauncher;
        this.sandNotificationAfterLessonJob = sandNotificationAfterLessonJob;
        this.sandNotificationBeforeLessonJob = sandNotificationBeforeLessonJob;
        this.updateRemainingCountJob = updateRemainingCountJob;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void runJobs() throws Exception {
        runJob(sandNotificationAfterLessonJob);
        runJob(sandNotificationBeforeLessonJob);
        runJob(updateRemainingCountJob);
    }

    private void runJob(Job job) throws Exception {
        JobParameters parameters = new JobParametersBuilder()
                .addString("jobName: ", job.getName() + System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(job, parameters);
    }
}