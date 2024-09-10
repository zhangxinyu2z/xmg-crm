package com._520it.crm.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 权限信息类
 */
@Setter
@Getter
public class Permission implements Serializable {
	private static final long serialVersionUID = 3243570982046050863L;
	/** 权限id */
	private Long id;

	/** 权限名称 */
	private String name;

	/** 资源路径 */
	private String resource;
}