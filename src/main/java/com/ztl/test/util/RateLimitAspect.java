package com.ztl.test.util;
import com.ztl.test.exception.RateLimitExceededProblem;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Aspect for handling simple rate limiting on annotated methods.
 * Limits requests based on a key and an allowed interval.
 */
@Aspect
@Component
public class RateLimitAspect {

    /**
     * Thread-safe storage of last request timestamps by key.
     */
    private static final ConcurrentHashMap<String, Long> requestTimestamps = new ConcurrentHashMap<>();

    /**
     * Rate limit checker. Checks that the time interval between requests does not exceed the configured limit.
     *
     * @param rateLimited the annotation containing key and interval information
     */
    @Before("@annotation(rateLimited)")
    public void rateLimitCheck(RateLimited rateLimited) {
        String key = rateLimited.key();
        long allowedInterval = rateLimited.interval(); // interval in milliseconds

        long now = System.currentTimeMillis();
        long lastRequestTime = requestTimestamps.getOrDefault(key, 0L);

        if (now - lastRequestTime < allowedInterval) {
            throw new RateLimitExceededProblem("Too many requests are made to this endpoint.");
        }

        requestTimestamps.put(key, now);
    }
}