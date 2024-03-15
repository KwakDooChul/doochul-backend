package org.doochul.application;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(final RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void delete(final String key) {
        redisTemplate.delete(key);
    }

    public boolean setNX(final String key, final String value, final Duration duration) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value, duration));
    }
}
