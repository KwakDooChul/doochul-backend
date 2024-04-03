package org.doochul.common.interceptor;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class APIRateLimiterInterceptor implements HandlerInterceptor {

    @Value("${bucket.plan}")
    private final String bucketPlan;
    private final BucketConfiguration bucketConfiguration;
    private final LettuceBasedProxyManager lettuceBasedProxyManager;

    @Override
    public boolean preHandle(final HttpServletRequest request, HttpServletResponse response, Object handler) {
        return checkBucketCounter(RatePlan.from(bucketPlan).getPlanName());
    }

    private boolean checkBucketCounter(final String key) {
        final Bucket bucket = bucket(key);
        if (!bucket.tryConsume(1)) {
            throw new IllegalArgumentException("잠시후 다시 시도해 주세요.");
        }
        return true;
    }

    private Bucket bucket(final String key) {
        return lettuceBasedProxyManager.builder().build(key.getBytes(), bucketConfiguration);
    }
}
