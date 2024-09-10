package com._520it.crm.query;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页请求数据
 * 
 * @author xinyu
 * @date 2021/06/19
 */
@Setter
@Getter
public class EmployeeQueryObject extends QueryObject {

	/** 员工高级查询关键字 */
	private String keyword;
}
