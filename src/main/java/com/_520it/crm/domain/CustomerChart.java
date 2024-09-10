package com._520it.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter@Getter
public class CustomerChart {
    private Long id;
    private String name;
    private Integer age;
    private Integer gender;
    private String tel;
    private String email;
    private String qq;
    private String wechat;
    private String job;
    private String salarylevel;
    private String customersource;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date inputtime;
    private Integer status;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date becometime;
    private Long inputuserId;
    private Long inchargeuserId;

    private Long amountCustomer;
    private String time;
    private Employee employee;


}