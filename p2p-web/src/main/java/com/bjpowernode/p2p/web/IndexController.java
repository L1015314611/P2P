package com.bjpowernode.p2p.web;

import com.bjpowernode.p2p.service.loan.LoanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: liuda
 * 2021/11/17
 * 首页控制层
 */
@Controller
public class IndexController {

    @Autowired
    private LoanInfoService loanInfoService;

    @RequestMapping(value = "/index")
    public String index(Model model){

        //获取平台历史平均年化收益率
        double historyAverageRate = loanInfoService.queryHistoryAverageRate();
        model.addAttribute("historyAverageRate",historyAverageRate);


        //获取平台注册总人数

        //获取平台累计投资金额

        //获取新手宝产品

        //获取优选产品

        //获取散标产品


        return "test";
    }
}
