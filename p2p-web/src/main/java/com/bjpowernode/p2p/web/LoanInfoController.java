package com.bjpowernode.p2p.web;

import com.bjpowernode.p2p.model.loan.BidInfo;
import com.bjpowernode.p2p.model.loan.LoanInfo;
import com.bjpowernode.p2p.model.user.FinanceAccount;
import com.bjpowernode.p2p.model.user.User;
import com.bjpowernode.p2p.model.vo.PaginationVO;
import com.bjpowernode.p2p.service.loan.BidInfoService;
import com.bjpowernode.p2p.service.loan.LoanInfoService;
import com.bjpowernode.p2p.service.user.FinanceAccountService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoanInfoController {

    @Resource
    private LoanInfoService loanInfoService;

    @Resource
    private BidInfoService bidInfoService;

    @Resource
    private FinanceAccountService financeAccountService;

    @RequestMapping("/loan/loan")
    public String loan(Model model,
                       @RequestParam(value = "ptype",required = false) Integer productType,
                       @RequestParam (value = "currentPage",defaultValue = "1") Integer currentPage,
                       @RequestParam(value = "pageSize",required = false) Integer pageSize){

        /**响应视图:返回值用string;如果返回字符串数据要加注解@ResponseBody*/
        /**MyBatis中如果一个方法有多个参数，只能用Map或者对象传递*/
        /**使用MySql中的limit(起始下标, 截取长度)    [ (pageNum-1)*PageSize,pageSize ]     */

        //准备分页查询的参数
        Map<String,Object> paramMap = new HashMap<String,Object>();
        //判断每页显示的条数是否有值:如果为空每页显示9条
        if (!ObjectUtils.allNotNull(pageSize)) {
            pageSize = 9;
        }
        paramMap.put("currentPage",(currentPage-1)*pageSize);//页码
        paramMap.put("pageSize",pageSize);//每页显示的条数
        //判断产品类型是否有值
        if (ObjectUtils.allNotNull(productType)) {
            paramMap.put("productType",productType);
        }

        //分页查询产品信息列表(产品类型，页码，每页显示的条数) -> 返回每页显示的数据:总记录数;分页模型对象PaginationVO
        PaginationVO<LoanInfo> paginationVO = loanInfoService.queryLoanInfoByPage(paramMap);

        //计算总页数
        int totalPage = paginationVO.getTotal().intValue() / pageSize;
        //求余数：如果大于0,页数加一;如果等于零即为整数
        int mod = paginationVO.getTotal().intValue() % pageSize;
        if (mod > 0) {
            totalPage = totalPage + 1;
        }

        model.addAttribute("totalRows",paginationVO.getTotal());
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("loanInfoList",paginationVO.getDataList());
        model.addAttribute("currentPage",currentPage);
        if (ObjectUtils.allNotNull(productType)) {
            model.addAttribute("productType",productType);
        }

        //TODO
        //用户投资排行榜

        return "loan";
    }

    @RequestMapping(value = "/loan/loanInfo")
    public String loanInfo(HttpServletRequest request, Model model,
                           @RequestParam (value = "id",required = true) Integer id) {

        /**响应视图:返回值用string;如果返回字符串数据要加注解@ResponseBody*/

        //根据产品标识获取产品详情
        LoanInfo loanInfo = loanInfoService.queryLoanInfoById(id);
        //根据产品标识获取该产品的所有投资记录
        List<BidInfo> bidInfoList = bidInfoService.queryBidInfoListByLoanId(id);





        
        model.addAttribute("loanInfo",loanInfo);
        model.addAttribute("bidInfoList",bidInfoList);

        /*//从session中获取用户信息
        User sessionUser = (User) request.getSession().getAttribute(Constants.SESSION_USER);

        //判断用户是否登录
        if (null != sessionUser) {

            //根据用户标识获取帐户资金信息
            FinanceAccount financeAccount = financeAccountService.queryFinanceAccountByUid(sessionUser.getId());
            model.addAttribute("financeAccount",financeAccount);
        }*/
        return "loanInfo";
    }















}
