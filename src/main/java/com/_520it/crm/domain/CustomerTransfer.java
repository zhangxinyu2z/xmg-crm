package com._520it.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户移交记录
 * 
 * @author xinyu
 * @date 2021/06/28
 */
@Setter
@Getter
public class CustomerTransfer implements Serializable {
	private static final long serialVersionUID = 2490983538010513739L;
	private Long id;

	/**
	 * 移交的客户
	 */
	private Customer customer;

	/**
	 * 移交客户的用户
	 */
	private Employee transuser;

	/**
	 * 移交时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date transtime;

	/**
	 * 移交前的市场专员
	 */
	private Employee oldseller;

	/**
	 * 移交后的市场专员
	 */
	private Employee newseller;

	/**
	 * 移交原因
	 */
	private String transreason;

}