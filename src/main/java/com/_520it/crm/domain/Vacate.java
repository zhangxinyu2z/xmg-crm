package com._520it.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 请假
 */
@Setter
@Getter
@Alias("Vacate")
public class Vacate {
    /**
     * 未审核
     */
    public static final int NORMAL = 0;
    /**
     * 已审核
     */
    public static final int AUDIT = 1;
    private Long id;

    /**
     * 审核状态
     */
    private int state;

    /**
     * 请假时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date begintime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endtime;

    /**
     * 审核时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date audittime;
    /**
     * 申请员工
     */
    private Employee employee;
    /**
     * 审核人
     */
    private Employee audit;

    @Override
    public String toString() {
        return "Vacate [id=" + id + ", state=" + state + ", begintime="
                + begintime + ", endtime=" + endtime + ", audittime="
                + audittime + "]";
    }
}