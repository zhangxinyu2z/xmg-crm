package com._520it.crm.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * 员工表
 * 
 * @author zhang xinyu
 * @date 2021/06/15
 */
@Setter
@Getter
public class Employee {
	private Long id;

	private String username;
	
	private String realname;

	private String password;

	private String tel;

	private String email;

	private Department dept;
	
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") // date转为json返回的是long,转成指定的格式
	@DateTimeFormat(pattern = "yyyy-MM-dd") // 将页面传递的字符串转成Date
	private Date inputtime;

	private Boolean state;

	private Boolean admin;
	
	/* 员工对应的角色 */
	private List<Role> roles = new ArrayList<>();

}