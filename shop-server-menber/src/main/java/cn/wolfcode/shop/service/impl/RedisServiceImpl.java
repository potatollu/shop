package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.redis.KeyPrefix;
import cn.wolfcode.shop.redis.UserKeyPrefix;
import cn.wolfcoe.shop.domain.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 */
@Service
public class RedisServiceImpl {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public void set(KeyPrefix prefix,String key,Object value){
        redisTemplate.opsForValue().set(getRealKey(prefix,key),value,prefix.getExpire(), TimeUnit.MILLISECONDS);
    }

    private String getRealKey(KeyPrefix prefix, String key) {
        return prefix.getPrefix() + ":" +key;
    }

    public void del(KeyPrefix prefix,String key){
        redisTemplate.delete(getRealKey(prefix,key));
    }

    public <T> T get(UserKeyPrefix prefix, String key) {
        return (T)redisTemplate.opsForValue().get(getRealKey(prefix,key));
    }

}
