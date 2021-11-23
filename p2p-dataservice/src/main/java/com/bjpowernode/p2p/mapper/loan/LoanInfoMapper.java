package com.bjpowernode.p2p.mapper.loan;

import com.bjpowernode.p2p.model.loan.LoanInfo;

public interface LoanInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_loan_info
     *
     * @mbggenerated Tue Nov 16 22:51:24 CST 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_loan_info
     *
     * @mbggenerated Tue Nov 16 22:51:24 CST 2021
     */
    int insert(LoanInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_loan_info
     *
     * @mbggenerated Tue Nov 16 22:51:24 CST 2021
     */
    int insertSelective(LoanInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_loan_info
     *
     * @mbggenerated Tue Nov 16 22:51:24 CST 2021
     */
    LoanInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_loan_info
     *
     * @mbggenerated Tue Nov 16 22:51:24 CST 2021
     */
    int updateByPrimaryKeySelective(LoanInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_loan_info
     *
     * @mbggenerated Tue Nov 16 22:51:24 CST 2021
     */
    int updateByPrimaryKey(LoanInfo record);
    /**
     * 获取首页面的平台历史平均年化收益率
     * */
    Double selectHistoryAverageRate();
}