package com._520it.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 保修单明细
 *
 * @author
 */
@Setter
@Getter
@Alias("GuaranteeItem")
public class GuaranteeItem {
    private Long id;
    /**
     * 保修时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date guaranteetime;
    /**
     * 保修内容
     */
    private String content;
    /**
     * 状态:是否解决
     */
    private Integer status;
    /**
     * 保修费用
     */
    private Integer cost;
    /**
     * 保修单号
     */
    private Guarantee guarantee;


}