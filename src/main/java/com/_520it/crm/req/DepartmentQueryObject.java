package com._520it.crm.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xinyu
 * @date 2021/06/23
 */
@Setter@Getter
public class DepartmentQueryObject extends PageReq {
	private String keyword;
	/**
	 * 状态：0：禁用 1：启用
	 */
	private Boolean state;
}
