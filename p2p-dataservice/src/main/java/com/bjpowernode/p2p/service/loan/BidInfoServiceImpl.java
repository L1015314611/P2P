package com.bjpowernode.p2p.service.loan;

import com.bjpowernode.p2p.constant.Constants;
import com.bjpowernode.p2p.mapper.loan.BidInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;

@Service
public class BidInfoServiceImpl implements BidInfoService{

    @Autowired
    private BidInfoMapper bidInfoMapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public Double queryTotalBidMoney() {

        //将redisTemplate模板对象存储的key的序列化方式修改为：StringRedisSerializer提高代码的可读性
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        //首先获取处理redis数据类型的某个Key的操作对象,此对象只能操作这个Key
        BoundValueOperations<Object, Object> boundValueOperations = redisTemplate.boundValueOps(Constants.TOTAL_BID_MONEY);
        //获取该操作对象对应的值
        Double totalBidMoney = (Double) boundValueOperations.get();
        if(true == ObjectUtils.isEmpty(totalBidMoney)){
            synchronized (this){
                totalBidMoney = (Double) boundValueOperations.get();
                if(true == ObjectUtils.isEmpty(totalBidMoney)){
                    totalBidMoney = bidInfoMapper.selectTotalBidMoney();
                    boundValueOperations.set(totalBidMoney,15,TimeUnit.SECONDS);
                }
            }
        }
        return totalBidMoney;
    }
}
