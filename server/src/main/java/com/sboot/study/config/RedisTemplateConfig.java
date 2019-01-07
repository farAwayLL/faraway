package com.sboot.study.config;

/**
 * create by faraway on 2019/1/7
 * description:
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisTemplate的自定义注入配置
 * 改造RedisTemplate源码中默认的序列化编码字符,注意：bean对象名称最好与注入使用的时候引用保持一致
 * Created by Administrator on 2018/9/29.
 */
@Configuration
public class RedisTemplateConfig {

    /**方法一*/

//    @Autowired
//    private RedisTemplate redisTemplate;
//    @Bean(name = "redisTemplate")
//    public RedisTemplate<String, Object> redisTemplate() {
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
//        return redisTemplate;
//    }

    /**方法二*/

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

}
