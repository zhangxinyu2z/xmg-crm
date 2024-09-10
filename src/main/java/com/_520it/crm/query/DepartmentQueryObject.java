package com._520it.crm.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xinyu
 * @date 2021/06/23
 */
@Setter@Getter
public class DepartmentQueryObject extends QueryObject {
	private String keyword;
	private Boolean state;
}
