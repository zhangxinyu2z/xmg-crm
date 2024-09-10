package com._520it.crm.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息类
 */
@Setter
@Getter
public class Role implements Serializable {
	private static final long serialVersionUID = -7696420095165807634L;
	/** 角色id */
	private Long id;

	/** 角色名称 */
	private String name;

	/** 角色编号 */
	private String sn;

	/**
	 * 角色对应的权限信息
	 * 
	 * 需要的页面参数格式是 permissions[o].id= 1才能完成映射
	 */
	private List<Permission> permissions = new ArrayList<>();
}