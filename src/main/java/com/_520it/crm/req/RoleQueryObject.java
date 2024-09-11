package com._520it.crm.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 角色表请求信息
 * @author xinyu
 * @date 2021/06/20
 */
@Setter@Getter
public class RoleQueryObject extends PageReq {
	private String keyword;
}
