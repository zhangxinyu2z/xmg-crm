package com._520it.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 考勤签到
 *
 * @author zhangxinyu
 */
@Setter
@Getter
@Alias("CheckIn")
public class CheckIn {

    /**
     * 正常
     */
    public static final Integer SIGNSTATE_NORMAL = 1;
    /**
     * 迟到
     */
    public static final Integer SIGNSTATE_LATE = 2;
    /**
     * 早退
     */
    public static final Integer SIGNSTATE_EARLY = 3;
    /**
     * 迟到+早退
     */
    public static final Integer SIGNSTATE_LATEANDEARLY = 4;

    //表示用来判断是补签修改还是 新增补签
    //private int checkid;

    private Long id;
    /**
     * 签到ip
     */
    private String userip;

    /**
     * 签到状态
     */
    private int state;

    /**
     * 签到时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date signintime;

    /**
     * 签退时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date signouttime;

    /**
     * 签到人
     */
    private Employee employee;

    /**
     * 补签时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date checktime;

    /**
     * 补签人
     */
    private Employee checkman;

    @Override
    public String toString() {
        return "CheckIn [id=" + id + ", userip=" + userip + ", state=" + state
                + ", signintime=" + signintime + ", signouttime=" + signouttime
                + ", checktime=" + checktime + "]";
    }
}