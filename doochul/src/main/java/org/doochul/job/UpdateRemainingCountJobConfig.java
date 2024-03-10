package org.doochul.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.Collections;

@Configuration
public class UpdateRemainingCountJobConfig {
    @Bean
    public Job updateRemainingCountJob(JobRepository jobRepository, PlatformTransactionManager transactionManager, JdbcTemplate jdbcTemplate) {
        return new JobBuilder("updateRemainingCountJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(updateRemainingCountStep(jobRepository, transactionManager, jdbcTemplate))
                .build();
    }

    @Bean
    public Step updateRemainingCountStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, JdbcTemplate jdbcTemplate) {
        return new StepBuilder("updateRemainingCountStep", jobRepository)
                .tasklet(new UpdateRemainingCountTasklet(jdbcTemplate), transactionManager)
                .build();
    }
    private static class UpdateRemainingCountTasklet implements Tasklet {

        private final JdbcTemplate jdbcTemplate;

        public UpdateRemainingCountTasklet(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        @Override
        public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
            jdbcTemplate.update("UPDATE lesson  JOIN member_ship m ON l.membership_id = m.id SET m.remaining_count = m.remaining_count - 1 WHERE l.ended_at <= :endedAt",
                    Collections.singletonMap("endedAt", LocalDateTime.now()));
            return RepeatStatus.FINISHED;
        }
    }
}
