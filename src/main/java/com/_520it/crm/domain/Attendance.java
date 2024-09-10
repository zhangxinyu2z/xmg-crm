package com._520it.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 考勤出勤记录
 *
 * @author zhangxinyu
 */
@Setter @Getter 
@Alias("Attendance")
public class Attendance {
    private Long id;
    /**
     * 出勤员工
     */
    private Employee employee;
    /**
     * 出勤天数
     */
    private Long signinday;
    /**
     * 迟到天数
     */
    private Long lateday;
    /**
     * 早退天数
     */
    private Long earlyday;

    /**
     * 请假天数
     */
    private Long vacateday;

    /**
     * 添加时间
     */
    @DateTimeFormat(pattern = "yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date date;
}