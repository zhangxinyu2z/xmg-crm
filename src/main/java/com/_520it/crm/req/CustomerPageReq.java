package com._520it.crm.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xinyu
 * @date 2021/06/22
 */
@Setter
@Getter
public class CustomerPageReq extends PageReq {
	/**
	 * 用户id,负责人
	 */
	private Long userid;

	/**
	 * 高级查询条件
	 * <p>
	 * 手机号或者用户姓名
	 */
	private String keyword;

	/**
	 * 客户状态
	 */
	private Integer status;
}
