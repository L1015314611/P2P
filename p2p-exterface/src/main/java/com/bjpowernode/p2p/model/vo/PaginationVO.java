package com.bjpowernode.p2p.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PaginationVO<T> implements Serializable {

    /**Lombok插件：idea下载插件,类上加注解@Data。
     * 使用Lombok插件可以不手写set get等方法,插件隐式自动生成显得对象类很简洁*/

    /**数据总记录数*/
    private Long total;

    /**每页显示的数据*/
    private List<T> dataList;

}
