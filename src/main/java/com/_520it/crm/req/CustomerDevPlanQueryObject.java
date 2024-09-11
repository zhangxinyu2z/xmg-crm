package com._520it.crm.req;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *
 * @author zhangxinyu
 */
@Setter
@Getter
public class CustomerDevPlanQueryObject extends PageReq {
    private Long userId;
    /** 关键字查询 */
    private String keyword;
    /** 开始时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    /** 结束时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    /** 跟进效果 */
    private Integer traceresult;
}
