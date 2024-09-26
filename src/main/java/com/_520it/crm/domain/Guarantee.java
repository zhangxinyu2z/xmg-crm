package com._520it.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 保修单
 *
 * @author zhangxinyu
 */
@Setter
@Getter
@Alias("Guarantee")
public class Guarantee {
    private Long id;
    /**
     * 保修单号
     */
    private String sn;
    /**
     * 产品名称
     * 应关联产品对象，暂时没有
     */
    private String productname;
    /**
     * 保修客户
     */
    private Customer customer;
    /**
     * 质保到期时间，默认从合同通过后1年
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date duetime;
    /**
     * 备注
     */
    private String remark;

    @Override
    public String toString() {
        return "Guarantee [id=" + id + ", sn=" + sn + ", productname="
                + productname + ", duetime=" + duetime + ", remark=" + remark
                + "]";
    }


}