package com.defu.permission.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * description:
 * create by: guardian_liu
 * date: 2019/5/23
 * time: 18:10
 */
@Component
@Slf4j
public class RedisUtil {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    JwtProperties jwtProperties;

    public void setString(String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }

    public void setString(String key,String value,long mills){
        redisTemplate.opsForValue().set(key,value,mills, TimeUnit.MILLISECONDS);
    }

    public void setStr(String key,String value,long mills){
        stringRedisTemplate.opsForValue().set(key,value,mills,TimeUnit.MILLISECONDS);
    }

    public boolean setNx(String key,String value,long mills){
        return redisTemplate.opsForValue().setIfAbsent(key,value,mills,TimeUnit.MILLISECONDS);
    }
    public String get(String key){
        if(null == redisTemplate.opsForValue().get(key)){
            return null;
        }
        return String.valueOf(redisTemplate.opsForValue().get(key));
    }

    public String getString(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 将token缓存到redis,过期时间写在配置文件中
     * @param key
     * @param token
     */
    public void setTokenToRedis(String key,String token){
        redisTemplate.opsForValue().set(jwtProperties.getKeyPrefix()+key,token,jwtProperties.getExpire(),TimeUnit.SECONDS);
    }

    /**
     *
     */
    public String getToken(String key){
        return redisTemplate.opsForValue().get(jwtProperties.getKeyPrefix()+key).toString();
    }

    /**
     * 删除缓存字符串
     * @param key
     * @return
     */
    public boolean deleteString(String key) {
        return stringRedisTemplate.delete(key);
    }

    /**
     * 是否过期
     * @param key
     * @return
     */
    public boolean isExpired(String key){
        long expire = redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
        if(expire<0){
            return false;
        }
        return true;
    }

}
