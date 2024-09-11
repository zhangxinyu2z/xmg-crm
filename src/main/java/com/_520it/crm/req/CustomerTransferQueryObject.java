package com._520it.crm.req;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xinyu
 * @date 2021/06/28
 */
@Getter
@Setter
public class CustomerTransferQueryObject extends PageReq {
	/**
	 * 客户姓名
	 */
	private String customername;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8") // date转为json返回的是long,转成指定的格式
	@DateTimeFormat(pattern = "yyyy-MM-dd") // 将页面传递的字符串转成Date
	private Date begintime;
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8") // date转为json返回的是long,转成指定的格式
	@DateTimeFormat(pattern = "yyyy-MM-dd") // 将页面传递的字符串转成Date
	private Date endtime;
}
