package com._520it.crm.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xinyu
 * @date 2021/06/22
 */
@Setter
@Getter
public class CustomerQueryObject extends PageReq {
	/**
	 * 用户id,负责人
	 */
	private Long userId;

	/**
	 * 高级查询条件
	 * <p>
	 * 手机号或者用户姓名
	 */
	private String keyword;

	/**
	 * 客户状态：-2:流失，-1:开发失败，0:潜在客户，1:正式客户，2:资源池客户
	 *
	 * 3：所有潜在客户
	 * 4：所有正式客户
	 */
	private Integer status;
}
