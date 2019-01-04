package com.sboot.study.service;

import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.sboot.study.entity.User;
import com.sboot.study.mybatisMapper.UserMapper;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * create by faraway on 2018/12/29
 * description:
 */
@Service
public class UserForRedisService {

    private static final Logger log = LoggerFactory.getLogger(UserForRedisService.class);

    @Autowired
    private UserMapper userMapper;

    //只能用于操作简单的kv，复杂的hash散列存储要用RedisTemplate
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 用户详情v1  无缓存
     * @param userId
     * @return
     */
    public User getUserDetailByIdV1(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    /**
     * 用户详情v2  永久缓存，会造成缓存数据量越来越大，不建议
     * @param userId
     * @return
     */
    public User getUserDetailByIdV2(Integer userId) throws Exception {
        //先查询缓存,再查数据库           user:info:key:1
        final String key=String.format("user:info:key:%s",userId);

        User user;
        if (stringRedisTemplate.hasKey(key)){
            //key存在于缓存
            String value=stringRedisTemplate.opsForValue().get(key);
            user=objectMapper.readValue(value,User.class);

        }else{
            //key不存在于缓存->查数据库并存入缓存
            user=userMapper.selectByPrimaryKey(userId);
            if (user!=null){
                stringRedisTemplate.opsForValue().set(key,objectMapper.writeValueAsString(user));
            }
        }
        return user;
    }

    /**
     * 用户详情- 设置过期时间以及解决缓存雪崩问题
     * @param userId
     * @return
     * @throws Exception
     */
    public User getUserDetailByIdV3(Integer userId) throws Exception{
        //TODO：先查询缓存，再查数据库
        final String key=String.format("user:info:key:%s",userId);

        User user;
        if (stringRedisTemplate.hasKey(key)){
            //TODO：key存在于缓存
            String value=stringRedisTemplate.opsForValue().get(key);
            user=objectMapper.readValue(value,User.class);

        }else{
            //TODO：key不存在于缓存->查数据库并存入缓存
            user=userMapper.selectByPrimaryKey(userId);
            if (user!=null){
                //设置key，value，过期时间，时间单位。但是这么设置有一个问题，如果缓存中的数据是高并发灌进来的数据，
                //因为过期时间都一样，在某个时间会集体失效，发生缓存雪崩
                //stringRedisTemplate.opsForValue().set(key,objectMapper.writeValueAsString(user),10, TimeUnit.SECONDS);
                /**解决办法：设置随机过期时间 240小时到250小时不等*/
                Long expire= RandomUtils.nextLong(240,250);
                stringRedisTemplate.opsForValue().set(key,objectMapper.writeValueAsString(user),expire, TimeUnit.HOURS);
                log.info("过期的随机时间：{} ",expire);
            }
        }

        return user;
    }

    /**
     * 用户详情- 解决缓存穿透问题
     * 缓存穿透：数据既不存在于缓存，也不存在于数据库，会造成无法缓存，但是会频繁的去数据库中查不存在的数据(洪流攻击 ddos攻击)，造成数据库宕机
     * 解决办法，将不存在的数据也缓存起来，设置较短的过期时间
     * @param userId
     * @return
     * @throws Exception
     */
    public User getUserDetailByIdV4(Integer userId) throws Exception{
        //TODO：先查询缓存，再查数据库
        final String key=String.format("user:info:key:%s",userId);

        User user = null;
        if (stringRedisTemplate.hasKey(key)){
            //TODO：key存在于缓存
            String value=stringRedisTemplate.opsForValue().get(key);
            if (!Strings.isNullOrEmpty(value)){
                user=objectMapper.readValue(value,User.class);
            }

        }else{
            //TODO：key不存在于缓存->查数据库并存入缓存
            user=userMapper.selectByPrimaryKey(userId);
            if (user!=null){
                //设置key，value，过期时间，时间单位。但是这么设置有一个问题，如果缓存中的数据是高并发灌进来的数据，
                //因为过期时间都一样，在某个时间会集体失效，发生缓存雪崩
                //stringRedisTemplate.opsForValue().set(key,objectMapper.writeValueAsString(user),10, TimeUnit.SECONDS);
                /**解决办法：设置随机过期时间 240小时到250小时不等*/
                Long expire= RandomUtils.nextLong(240,250);
                stringRedisTemplate.opsForValue().set(key,objectMapper.writeValueAsString(user),expire, TimeUnit.HOURS);
                log.info("过期的随机时间：{} ",expire);
            } else {
                /**解决缓存穿透的问题*/
                Long expire= RandomUtils.nextLong(10,20);
                stringRedisTemplate.opsForValue().set(key,"",expire, TimeUnit.SECONDS);
            }
        }

        return user;
    }

    public void updateCache(Integer userId) throws Exception {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user!=null){
            final String key = String.format("user:info:key:%s",userId);
            stringRedisTemplate.opsForValue().set(key,objectMapper.writeValueAsString(user), RandomUtil.randomLong(20,30),TimeUnit.SECONDS);
        }
    }
}
