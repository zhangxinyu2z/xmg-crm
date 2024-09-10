package com._520it.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

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

	// 响应：date转为json返回的是long,转成指定的格式
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	// 请求：将页面传递的字符串转成Date
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date inputtime;

	private Boolean state;

	private Boolean admin;
	
	/** 员工对应的角色 */
	private List<Role> roles = new ArrayList<>();

}