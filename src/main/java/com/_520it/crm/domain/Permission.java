package com._520it.crm.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 权限信息类
 */
@Setter
@Getter
public class Permission {
	/** 权限id */
	private Long id;

	/** 权限名称 */
	private String name;

	/** 资源路径 */
	private String resource;
}