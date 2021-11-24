package com.bjpowernode.p2p.web;

import com.bjpowernode.p2p.constant.Constants;
import com.bjpowernode.p2p.service.loan.BidInfoService;
import com.bjpowernode.p2p.service.loan.LoanInfoService;
import com.bjpowernode.p2p.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: liuda
 * 2021/11/17
 * 首页控制层
 */
@Controller
public class IndexController {

    @Resource
    private LoanInfoService loanInfoService;
    @Resource
    private UserService userService;
    @Resource
    private BidInfoService bidInfoService;

    @RequestMapping(value = "/index")
    public String index(Model model){

        /*//创建一个固定的线程池 指定线程池中线程的数量为100个
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for(int i =0;i<10000;i++){
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    double historyAverageRate = loanInfoService.queryHistoryAverageRate();
                    model.addAttribute(Constants.HISTORY_AVERAGE_RATE,historyAverageRate);
                }
            });
        }
        executorService.shutdownNow();//执行完销毁*/

        //获取平台历史平均年化收益率
        double historyAverageRate = loanInfoService.queryHistoryAverageRate();
        model.addAttribute(Constants.HISTORY_AVERAGE_RATE,historyAverageRate);

        //获取平台注册总人数
        Long allUserCount = userService.queryAllUserCount();
        model.addAttribute(Constants.ALL_USER_COUNT,allUserCount);

        //获取平台累计投资金额
        Double totalBidMoney = bidInfoService.queryTotalBidMoney();
        model.addAttribute(Constants.TOTAL_BID_MONEY,totalBidMoney);

        //将以下查询看作是一个分页，但实际上他们不是一个分页
        //根据产品类型获取产品信息列表(产品类型，页码，每页显示条数)
        /**MyBatis中如果一个方法有多个参数，只能用Map或者对象传递*/
        /**使用MySql中的limit    (pageNum-1)PageSize     */
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("currentPage",0);//页码
        //获取新手宝产品，产品类型为0，显示第1页，每页显示1个
        paramMap.put("productType",Constants.PRODUCT_TYPE_X);



        //获取优选产品，产品类型为1，显示第1页，每页显示4个



        //获取散标产品，产品类型为2，显示第1页，每页显示8个




        return "test";
    }
}
