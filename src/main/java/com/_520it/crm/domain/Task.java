package com._520it.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@Alias("Task")
public class Task {
    public static final int INIT = 0;
    public static final int COMPLETE = 1;
    public static final int LOSE = 2;
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end;
    private Employee handleuser;
    private String title;
    private String remark;
    private String mintaskdescribe;
    private String minhandledescribe;
    private int status = 0;
    private Integer dayDelta;
}