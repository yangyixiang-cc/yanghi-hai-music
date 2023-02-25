package com.yanghi.haimusic.service;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootTest
public class RedisTest {

    //注入redis操作对象
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test(){
        redisTemplate.opsForValue().set("redis","hello");
        Object redis = redisTemplate.opsForValue().get("redis");
        System.out.println(redis.toString());
    }
}
