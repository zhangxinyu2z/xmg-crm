package com._520it.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 合同模型
 * @author zhangxinyu
 */
@Getter
@Setter
public class Contract {
    private Long id;

    /**
     * 合同单号
     */
    private String sn;

    /**
     * 合同客户
     */
    private Customer customer;
    /**
     * 签订时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date signtime;

    /**
     * 营销人员
     */
    private Employee seller;

    /**
     * 合同金额
     */
    private String contractsum;

    /**
     * 付款金额
     */
    private String money;
    /**
     * 付款时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date paytime;

    /**
     * 合同摘要
     */
    private String intro;

    /**
     * 审核状态
     */
    private Integer status;

    /**
     * 附件
     */
    private String file;

    /**
     * 最近修改人
     */
    private Employee modifyuser;
    /**
     * 最近修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifytime;
}