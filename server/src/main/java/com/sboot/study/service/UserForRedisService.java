package com.sboot.study.service;

import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.sboot.study.entity.User;
import com.sboot.study.mybatisMapper.UserMapper;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
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

    //只能用于操作简单的kv：简单String型value值
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //复杂的：比如hash散列等value值要用RedisTemplate
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 用户详情v1  无缓存  每次都会向数据库发起请求
     *
     * @param userId
     * @return
     */
    public User getUserDetailByIdV1(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    /**
     * 用户详情v2  永久缓存
     * 永久缓存会造成缓存数据量越来越大,不建议使用(一般小项目，缓存量不大可以使用)
     *
     * @param userId
     * @return
     */
    public User getUserDetailByIdV2(Integer userId) throws Exception {
        //1.生成redis中的key
        /**对于%s解释：https://www.cnblogs.com/Dhouse/p/7776780.html*/
        final String key = String.format("user:info:key:%s", userId);
        //从redis中查询该key
        Boolean flag = stringRedisTemplate.hasKey(key);
        //如果存在，则去redis中获取 OPS，全称为：Open Pluggable Specification，开放式可插拔规范。
        User user;
        if (flag) {
            String value = stringRedisTemplate.opsForValue().get(key);
            user = objectMapper.readValue(value, User.class);
        } else {
            //如果不存在，先去数据库查询
            user = userMapper.selectByPrimaryKey(userId);
            //如果对象不为空
            if (user != null) {
                stringRedisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(user));
            }
        }
        return user;
    }

    /**
     * 缓存雪崩：redis中的缓存在同一时间大量失效，造成大量查询落在数据库，给数据库造成压力
     * 解决办法: 1.入缓存时不要设置统一时长，应设置随机时长，防止同一时间大量缓存失效；
     * 2.建立备份缓存，A缓存设置随机时长，B缓存设置永久时长，当A宕机，则查询B，并将缓存按随机时长策略灌入A
     *
     * @param userId
     * @return
     * @throws Exception description：用户详情- 设置过期时间以及解决缓存雪崩问题
     */
    public User getUserDetailByIdV3(Integer userId) throws Exception {
        String key = String.format("user:info:key:%s", userId);
        boolean flag = stringRedisTemplate.hasKey(key);
        //如果存在，则从缓存中获取
        User user;
        if (flag) {
            //获取缓存字符串
            String value = stringRedisTemplate.opsForValue().get(key);
            //String to Object
            user = objectMapper.readValue(value, User.class);
        } else {
            //如果不存在，则先查询数据库
            user = userMapper.selectByPrimaryKey(userId);
            /**比如此时有100W个数据同时缓存进来，过期时间都是1小时，1小时之后，100W数据同时失效，这里就会造成缓存雪崩
             stringRedisTemplate.opsForValue().set(key,objectMapper.writeValueAsString(user),1,TimeUnit.HOURS);*/
            Long time = RandomUtil.randomLong(240, 250);
            //然后将该对象存入数据库中,设置随机时间为10-12天不等
            stringRedisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(user), RandomUtil.randomLong(10, 12), TimeUnit.DAYS);
        }
        return user;
    }


    /**
     * 缓存穿透：数据既不存在于缓存，也不存在于数据库，会造成无法缓存。但是会频繁的去数据库中查不存在的数据(洪流攻击 ddos攻击)，造成数据库宕机
     * 用户详情- 解决缓存穿透问题
     * 解决办法，将不存在的数据也缓存起来，并设置较短的过期时间
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public User getUserDetailByIdV4(Integer userId) throws Exception {
        /**先查询缓存，再查数据库*/
        final String key = String.format("user:info:key:%s", userId);

        User user = null;
        if (stringRedisTemplate.hasKey(key)) {
            /**key存在于缓存*/
            String value = stringRedisTemplate.opsForValue().get(key);
            if (!Strings.isNullOrEmpty(value)) {
                user = objectMapper.readValue(value, User.class);
            }

        } else {
            /**key不存在于缓存->查数据库并存入缓存*/
            user = userMapper.selectByPrimaryKey(userId);
            if (user != null) {
                //设置key，value，过期时间，时间单位。但是这么设置有一个问题，如果缓存中的数据是高并发灌进来的数据，
                //因为过期时间都一样，在某个时间会集体失效，发生缓存雪崩
                //stringRedisTemplate.opsForValue().set(key,objectMapper.writeValueAsString(user),10, TimeUnit.SECONDS);
                /**解决办法：设置随机过期时间 240小时到250小时不等*/
                Long expire = RandomUtils.nextLong(240, 250);
                stringRedisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(user), expire, TimeUnit.HOURS);
                log.info("过期的随机时间：{} ", expire);
            } else {
                /**解决缓存穿透的问题*/
                stringRedisTemplate.opsForValue().set(key, "", 10, TimeUnit.SECONDS);
            }
        }

        return user;
    }

    public void insertUpdate(Integer userId) throws Exception {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user != null) {
            final String key = String.format("user:info:key:%s", userId);
            stringRedisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(user), RandomUtil.randomLong(20, 30), TimeUnit.SECONDS);
        }
    }

    /**
     * ----------------------------使用RedisTemplate存储hash----------------------------------
     */

    public User getUserDetailByIdV5(Integer userId) throws Exception {
        User user = null;
        //获取所有的大key  建议使用String,String,String 前两个一般都是String,第三个可能是对象 通过objectMapper.writeValueAsString(Object)序列化成字符串
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        //定义自己的大key
        String maxKey = "user:info";

        //如果自己的大key存在于缓存中,则查询缓存
        //if (redisTemplate.hasKey(maxKey)&&hashOperations.hasKey(maxKey,String.valueOf(userId))){
        if (hashOperations.hasKey(maxKey, String.valueOf(userId))) {
            String userStr = hashOperations.get(maxKey, String.valueOf(userId));
            if (StringUtils.isNoneEmpty(userStr)) {
                //如果对象不为空反序列化成对象
                user = objectMapper.readValue(userStr, User.class);
            }
        } else {
            //如果不存在缓存中，先从数据库中获取
            user = userMapper.selectByPrimaryKey(userId);
            if (user != null) {
                //对象不为空则存入缓存(将对象序列化成字符串)       key:value  value-->key:value
                hashOperations.putIfAbsent(maxKey, String.valueOf(userId), objectMapper.writeValueAsString(user));
            } else {
                //对象为空也存入缓存，防止缓存穿透
                hashOperations.putIfAbsent(maxKey, String.valueOf(userId), "");
            }
        }
        //获取大key所有的kv值
        System.out.println(hashOperations.entries(maxKey));
        return user;
    }

}
