package com._520it.crm.req;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by ShikieikiSama丶 on 2016/9/20.
 */

@Getter
@Setter
public class OrderBillQueryObject extends QueryObject{
    private Integer status;
    private String keyword; // 关键字查询
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate; // 开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate; // 结束时间
}
