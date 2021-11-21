package com.bjpowernode.p2p.service.loan;

import com.bjpowernode.p2p.mapper.loan.LoanInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;

@Service("loanInfoServiceImpl")
public class LoanInfoServiceImpl implements LoanInfoService {

    @Autowired
    private LoanInfoMapper loanInfoMapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public double queryHistoryAverageRate() {

        /*首先去redis缓存中查询，有：直接使用，没有：去数据库查询并存放到redis缓存中
        好处：提升系统的性能，提升用户的体验*/
        //首先redis缓存中获取该值
        double historyAverageRate = (double) redisTemplate.opsForValue().get("historyAverageRate");
        if(true == ObjectUtils.isEmpty(historyAverageRate)){
            historyAverageRate = loanInfoMapper.selectHistoryAverageRate();
            redisTemplate.opsForValue().set("historyAverageRate",historyAverageRate,15, TimeUnit.MINUTES);
        }

        return historyAverageRate;
    }
}
