package org.ozbeman.ebento.services;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class OtpStoreService {

    private final StringRedisTemplate redisTemplate;
    private final Integer OTP_TTL = 120;

    public OtpStoreService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(String key, String value) {
        redisTemplate.opsForValue().set(key, value, OTP_TTL, TimeUnit.SECONDS);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
