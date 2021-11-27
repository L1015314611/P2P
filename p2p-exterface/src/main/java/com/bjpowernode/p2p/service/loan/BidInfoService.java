package com.bjpowernode.p2p.service.loan;

import com.bjpowernode.p2p.model.loan.BidInfo;

import java.util.List;

public interface BidInfoService {

    /**获取平台投资总金额*/
    Double queryTotalBidMoney();
    /**根据产品标识获取该产品的所有投资记录*/
    List<BidInfo> queryBidInfoListByLoanId(Integer id);
}
