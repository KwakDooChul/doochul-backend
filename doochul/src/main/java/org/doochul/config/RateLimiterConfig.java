package org.doochul.config;

import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import lombok.extern.slf4j.Slf4j;
import org.doochul.common.interceptor.RatePlan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class RateLimiterConfig implements WebMvcConfigurer {
    @Value("${spring.data.redis.host}")
    private String redisHost;
    @Value("${spring.data.redis.port}")
    private int redisPort;
    @Value("${bucket.plan}")
    private String bucketPlan;

    //TODO: RIOT API구현하면 추가해주기
//    @Override
//    public void addInterceptors(final InterceptorRegistry registry) {
//        registry.addInterceptor(new APIRateLimiterInterceptor(bucketPlan, bucketConfiguration(), lettuceBasedProxyManager()))
//                .addPathPatterns("/**")
//                .excludePathPatterns("/login/kakao", "/oauth/kakao");
//    }

    @Bean
    public RedisClient redisClient() {
        final RedisURI redisUri = RedisURI.create(redisHost, redisPort);
        return RedisClient.create(redisUri);
    }

    @Bean
    public LettuceBasedProxyManager lettuceBasedProxyManager() {
        return LettuceBasedProxyManager
                .builderFor(redisClient())
                .withExpirationStrategy(ExpirationAfterWriteStrategy.
                        basedOnTimeForRefillingBucketUpToMax(RatePlan.from(bucketPlan).getRefillTime()))
                .build();
    }

    @Bean
    public BucketConfiguration bucketConfiguration() {
        return BucketConfiguration.builder()
                .addLimit(RatePlan.resolvePlan(bucketPlan))
                .build();
    }
}
