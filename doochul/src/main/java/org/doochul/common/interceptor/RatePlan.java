package org.doochul.common.interceptor;

import io.github.bucket4j.Bandwidth;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum RatePlan {

    TEST("test", Duration.ofMinutes(60)) {
        @Override
        public Bandwidth getLimit() {
            return Bandwidth.builder()
                    .capacity(10)
                    .refillIntervallyAligned(10, Duration.ofSeconds(1), Instant.now())
                    .build();
        }
    },
    PRODUCTION("production", Duration.ofMinutes(60)) {
        @Override
        public Bandwidth getLimit() {
            return Bandwidth.builder()
                    .capacity(30000)
                    .refillIntervallyAligned(100, Duration.ofSeconds(1), Instant.now())
                    .build();
        }
    },
    PERSONAL("personal", Duration.ofMinutes(2)) {
        @Override
        public Bandwidth getLimit() {
            return Bandwidth.builder()
                    .capacity(100)
                    .refillIntervallyAligned(20, Duration.ofSeconds(1), Instant.now())
                    .build();
        }
    },

    ;

    public abstract Bandwidth getLimit();

    private final String planName;
    private final Duration refillTime;

    public static RatePlan from(final String targetPlan) {
        return Arrays.stream(RatePlan.values())
                .filter(ratePlan -> ratePlan.getPlanName().equals(targetPlan))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("정의되지 않은 정책입니다."));
    }

    public static Bandwidth resolvePlan(final String targetPlan) {
        return Arrays.stream(RatePlan.values())
                .filter(ratePlan -> ratePlan.getPlanName().equals(targetPlan))
                .map(RatePlan::getLimit)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("정의되지 않은 정책입니다."));
    }
}
