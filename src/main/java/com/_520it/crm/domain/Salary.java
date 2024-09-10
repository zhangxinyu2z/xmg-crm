package com._520it.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工工资
 * @author zhangxinyu
 */
@Setter @Getter
@Alias("Salary")
public class Salary {
	
    private Long id;
    private Long eid ;
    private BigDecimal salary;
    /**
     * 员工
     */
    private Employee employee;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;
    
    @DateTimeFormat(pattern = "yyyy")
    @JsonFormat(pattern = "yyyy", timezone = "GMT+8")
    private Date year ;
    
    @DateTimeFormat(pattern = "MM")
    @JsonFormat(pattern = "MM", timezone = "GMT+8")
    private Date month ;
    
}