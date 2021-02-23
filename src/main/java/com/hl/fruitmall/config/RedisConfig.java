package com.hl.fruitmall.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Hl
 * @create 2020/12/21 20:31
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        // 使用这个序列化和反序列化不需要在进行一次JONSObject的转换
//        redisTemplate.setDefaultSerializer(new GenericFastJsonRedisSelizer());
        // 下面这些要
        redisTemplate.setKeySerializer(new FastJsonRedisSerializer<>(Object.class));
        redisTemplate.setValueSerializer(new FastJsonRedisSerializer<>(Object.class));
        redisTemplate.setHashKeySerializer(new FastJsonRedisSerializer<>(Object.class));
        redisTemplate.setHashValueSerializer(new FastJsonRedisSerializer<>(Object.class));
        redisTemplate.setConnectionFactory(connectionFactory);
//        redisTemplate.setEnableTransactionSupport(true); // 不要在配置类开启事务
        return redisTemplate;
    }
}
