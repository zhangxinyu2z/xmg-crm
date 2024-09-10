package com._520it.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * 订单模型
 *
 * @author zhangxinyu
 */
@Getter
@Setter
@Alias("OrderBill")
public class OrderBill {
    private Long id;
    /**
     * 签订时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date signtime;

    /**
     * 定金客户
     */
    private Customer customer;

    /**
     * 营销人员,自动关联
     */
    private Employee seller;

    /**
     * 总金额
     */
    private String totalsum;

    /**
     * 定金金额
     */
    private String bargainmoney;

    /**
     * 摘要
     */
    private String intro;

    /**
     * 附件,记录文件的位置,如纸质订单的截图
     */
    private String file;
    /**
     * 创建时间,前台不显示,新增的时候自动填充
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createtime;

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

    /**
     * 订单状态:
     * 0:初始录入
     * 1:已生成合同
     * 2:已退款
     */
    private Integer status;
}