package com._520it.crm.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xinyu
 * @date 2021/06/23
 */
@Setter@Getter
public class DepartmentPageReq extends PageReq {
	private String keyword;
	private Boolean state;
}
