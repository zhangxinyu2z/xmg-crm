package com._520it.crm.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xinyu
 * @date 2021/06/19
 */
@Setter
@Getter
public class PermissionQueryObject extends PageReq {
	/** 角色id */
	private Long rid;
}
