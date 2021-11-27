package com.bjpowernode.p2p.service.loan;
/**
 * ClassName:LoanInfoService
 * Package:com.bjpowernode.p2p.service.loan
 * Description:
 *
 * @date:2021/11/17 22:55
 * @author:
 */

import com.bjpowernode.p2p.model.loan.LoanInfo;
import com.bjpowernode.p2p.model.vo.PaginationVO;

import java.util.List;
import java.util.Map;

/**
 * Author: liuda
 * 2021/11/17
 */
public interface LoanInfoService {

    /**获取首页面的平台历史平均年化收益率*/
    double queryHistoryAverageRate();
    /**根据产品类型获取产品列表*/
    List<LoanInfo> queryLoanInfoListByProductType(Map<String, Object> paramMap);
    /**分页查询产品信息列表*/
    PaginationVO<LoanInfo> queryLoanInfoByPage(Map<String, Object> paramMap);
    /**根据产品表示获取产品详情*/
    LoanInfo queryLoanInfoById(Integer id);

}
