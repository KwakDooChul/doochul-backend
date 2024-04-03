package org.doochul.common.interceptor;

import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.concurrent.atomic.AtomicInteger;

class APIRateLimiterInterceptorTest {

    private APIRateLimiterInterceptor interceptor;
    private BucketConfiguration bucketConfiguration;
    private LettuceBasedProxyManager lettuceBasedProxyManager;

    @BeforeEach
    void setUp() {
        final RedisClient redisClient = RedisClient.create(RedisURI.create("redis://localhost:6379"));
        bucketConfiguration = BucketConfiguration.builder()
                .addLimit(RatePlan.TEST.getLimit())
                .build();
        lettuceBasedProxyManager = LettuceBasedProxyManager.builderFor(redisClient)
                .withExpirationStrategy(ExpirationAfterWriteStrategy.
                        basedOnTimeForRefillingBucketUpToMax(RatePlan.TEST.getRefillTime()))
                .build();
        interceptor = new APIRateLimiterInterceptor("test", bucketConfiguration, lettuceBasedProxyManager);
    }

    @Test
    @DisplayName("Test정책의 따른 초과된 요청은 에러반환 테스트")
    void testPreHandle_withEnoughTokens() {
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();
        final AtomicInteger errorCount = new AtomicInteger(0);
        for (int i = 0; i < 15; i++) {
            try {
                interceptor.preHandle(request, response, null);
            } catch (IllegalArgumentException e) {
                errorCount.incrementAndGet();
            }
        }
        Assertions.assertThat(errorCount.incrementAndGet()).isEqualTo(6);
    }


}