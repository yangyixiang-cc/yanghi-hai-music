package com.yanghi.haimusic.service;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;


@SpringBootTest
public class RedisTest {

    //注入redis操作对象
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test(){
        redisTemplate.opsForValue().set("redis", "hello", Duration.ofMinutes(1L));
        Object redis = redisTemplate.opsForValue().get("redis");
        System.out.println(redis.toString());
    }
}
