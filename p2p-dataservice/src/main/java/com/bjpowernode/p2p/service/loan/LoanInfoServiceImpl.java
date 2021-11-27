package com.bjpowernode.p2p.service.loan;

import com.bjpowernode.p2p.constant.Constants;
import com.bjpowernode.p2p.mapper.loan.LoanInfoMapper;
import com.bjpowernode.p2p.model.loan.LoanInfo;
import com.bjpowernode.p2p.model.vo.PaginationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service("loanInfoServiceImpl")
public class LoanInfoServiceImpl implements LoanInfoService {

    @Autowired
    private LoanInfoMapper loanInfoMapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public double queryHistoryAverageRate() {

        //将redisTemplate模板对象存储的key的序列化方式修改为：StringRedisSerializer提高代码的可读性
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        /*首先去redis缓存中查询，有：直接使用，没有：去数据库查询并存放到redis缓存中
        好处：提升系统的性能，提升用户的体验*/
        //首先redis缓存中获取该值
        /*Double historyAverageRate = (Double) redisTemplate.opsForValue().get(Constants.HISTORY_AVERAGE_RATE);
        if(true == ObjectUtils.isEmpty(historyAverageRate)){
            historyAverageRate = loanInfoMapper.selectHistoryAverageRate();
            redisTemplate.opsForValue().set(Constants.HISTORY_AVERAGE_RATE,historyAverageRate,15, TimeUnit.MINUTES);
        }*/

        /**以上代码在多线程高并发的时候会出现缓存穿透问题；
         * 可以通过双重检测+同步代码块的方式解决
         * */
        Double historyAverageRate = (Double) redisTemplate.opsForValue().get(Constants.HISTORY_AVERAGE_RATE);
        if(true == ObjectUtils.isEmpty(historyAverageRate)){
            synchronized (this){
                historyAverageRate = (Double) redisTemplate.opsForValue().get(Constants.HISTORY_AVERAGE_RATE);
                if(true == ObjectUtils.isEmpty(historyAverageRate)){
                    historyAverageRate = loanInfoMapper.selectHistoryAverageRate();
                    redisTemplate.opsForValue().set(Constants.HISTORY_AVERAGE_RATE,historyAverageRate,15, TimeUnit.MINUTES);
                }
            }
        }
        return historyAverageRate;
    }

    @Override
    public List<LoanInfo> queryLoanInfoListByProductType(Map<String, Object> paramMap) {

        return loanInfoMapper.queryLoanInfoListByProductType(paramMap);
    }

    @Override
    public PaginationVO<LoanInfo> queryLoanInfoByPage(Map<String, Object> paramMap) {

        PaginationVO<LoanInfo> paginationVO = new PaginationVO<>();
        Long total = loanInfoMapper.selectTotal(paramMap);
        paginationVO.setTotal(total);
        List<LoanInfo> loanInfoList = loanInfoMapper.selectLoanInfoByPage(paramMap);
        paginationVO.setDataList(loanInfoList);
        return paginationVO;
    }

    @Override
    public LoanInfo queryLoanInfoById(Integer id) {

        return loanInfoMapper.selectByPrimaryKey(id);
    }
}
