package com._520it.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class Customer implements Serializable {
	private static final long serialVersionUID = -6885172181938395414L;
	private Long id;

	private String name;

	private Integer age;

	private Boolean gender;

	private String tel;

	private String email;

	private String qq;

	private String wechat;

	private String job;
	
	/**
	 * 薪资水平
	 */
	private String salarylevel;

	/**
	 * 客户来源
	 */
	private String customersource;
	
	/**
	 * 负责人
	 */
	private Employee inchargeuser;

	/**
	 * 创建人
	 */
	private Employee inputuser;
	
	/**
	 * 录入时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8") // 转成json是long的
	private Date inputtime;

	/**
	 * 客户状态 -2:流失 -1:开发失败 0:潜在客户 1:正式客户 2:资源池客户
	 */
	private Byte status;
	
	/**
	 * 转正时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8") // 转成json是long的
	private Date becometime;

}