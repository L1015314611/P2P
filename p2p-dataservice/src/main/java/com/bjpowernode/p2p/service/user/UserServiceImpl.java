package com.bjpowernode.p2p.service.user;

import com.bjpowernode.p2p.constant.Constants;
import com.bjpowernode.p2p.mapper.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public Long queryAllUserCount() {

        //将redisTemplate模板对象存储的key的序列化方式修改为：StringRedisSerializer提高代码的可读性
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        long allUserCount = (long) redisTemplate.opsForValue().get(Constants.ALL_USER_COUNT);
        if(true == ObjectUtils.isEmpty(allUserCount)){
            synchronized (this){
                allUserCount = (long) redisTemplate.opsForValue().get(Constants.ALL_USER_COUNT);
                if(true == ObjectUtils.isEmpty(allUserCount)){
                    allUserCount = userMapper.selectAllUserCount();
                    redisTemplate.opsForValue().set(Constants.ALL_USER_COUNT,allUserCount, 15,TimeUnit.MINUTES);
                }
            }
        }
        return allUserCount;
    }
}
