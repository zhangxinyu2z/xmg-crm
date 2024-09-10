package com._520it.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 潜在客户开发模型
 *
 * @author zhangxinyu
 */
@Setter
@Getter
@Alias("CustomerDevPlan")
public class CustomerDevPlan {
    /**
     * 编号
     */
    private Long id;
    /** 计划时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date plantime;
    /**
     * 计划主题
     */
    private String plansubject;
    /**
     * 计划实施方式:比如电话,邀约上门
     */
    private String plantype;
    /**
     * 计划内容
     */
    private String plandetails;
    /**
     * 跟进效果：优--3,中--2,差--1
     */
    private Integer traceresult;
    /**
     * 备注
     */
    private String remark;
    /**
     * 潜在客户
     */
    private Customer customer;
    /**
     * 创建人
     */
    private Employee inputuser;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date inputtime;
    /**
     * 类型:0:潜在开发计划;1:客户跟进历史,即正式客户开发计划
     */
    private Integer type;

}