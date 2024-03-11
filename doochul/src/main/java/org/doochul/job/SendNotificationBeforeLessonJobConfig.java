package org.doochul.job;

import org.doochul.service.Letter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.doochul.service.LessonStatus.BEFORE_LESSON;

@Configuration
public class SendNotificationBeforeLessonJobConfig {
    private static final int CHUNK_SIZE = 10;

    private final DataSource dataSource;
    private final SendNotificationItemWriter sendNotificationItemWriter;

    public SendNotificationBeforeLessonJobConfig(final DataSource dataSource, final SendNotificationItemWriter sendNotificationItemWriter) {
        this.dataSource = dataSource;
        this.sendNotificationItemWriter = sendNotificationItemWriter;
    }

    @Bean
    public Job sandNotificationBeforeLessonJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new JobBuilder("sandNotificationBeforeLessonJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(sendNotificationBeforeLessonStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step sendNotificationBeforeLessonStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager
    ) throws Exception {
        return new StepBuilder("addNotificationStep", jobRepository)
                .<Map<String, Object>, Letter>chunk(CHUNK_SIZE, transactionManager)
                .reader(addNotificationItemReader())
                .processor(addNotificationItemProcessor())
                .writer(sendNotificationItemWriter)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public JdbcPagingItemReader<Map<String, Object>> addNotificationItemReader() throws Exception {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("startedAt", LocalDateTime.now().plusMinutes(10));

        return new JdbcPagingItemReaderBuilder<Map<String, Object>>()
                .name("addNotificationItemReader")
                .dataSource(dataSource)
                .pageSize(CHUNK_SIZE)
                .queryProvider(createQueryProvider())
                .parameterValues(parameterValues)
                .rowMapper(new ColumnMapRowMapper())
                .build();
    }

    private PagingQueryProvider createQueryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("SELECT u.device_token AS student_token, u.name AS student_name, t.name AS teacher_name, l.started_at");
        queryProvider.setFromClause("FROM lesson l JOIN users u ON l.student_id = u.id JOIN users t ON l.teacher_id = t.id");
        queryProvider.setWhereClause("WHERE l.started_at <= :startedAt");
        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("l.started_at", Order.ASCENDING);

        queryProvider.setSortKeys(sortKeys);
        return Optional.ofNullable(queryProvider.getObject()).orElseThrow(IllegalArgumentException::new);
    }

    @Bean
    public ItemProcessor<Map<String, Object>, Letter> addNotificationItemProcessor() {
        return item -> {
            String studentToken = (String) item.get("student_token");
            String studentName = (String) item.get("student_name");
            String teacherName = (String) item.get("teacher_name");
            LocalDateTime startedAt = (LocalDateTime) item.get("started_at");
            return Letter.of(studentToken, studentName, teacherName, startedAt, BEFORE_LESSON);
        };
    }
}
